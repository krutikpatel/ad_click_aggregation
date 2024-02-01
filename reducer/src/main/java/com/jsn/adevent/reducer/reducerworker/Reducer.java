package com.jsn.adevent.reducer.reducerworker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import com.jsn.adevent.reducer.grpc.AdEvent;
import com.jsn.adevent.reducer.reducerworker.GrpcServer;
import com.jsn.adevent.reducer.kafka.MessagePublisher;
import com.jsn.adevent.reducer.model.AdClickCount;

/*
 * "Tumbling Window" of 1 minuteprevMap
 * 
 * May be received events just gets dumped into queue, and reducer picks up from there
 *  -because window flush is time consuming and we don't want to block the receiver
 * 
 * maintain 2 windows - 1 minute each
 *  1. anytime timestamp minute changes. switch the windows and flush other one
 */
@Component
public class Reducer {
    Logger logger = LogManager.getLogger(Reducer.class);
    ArrayBlockingQueue<AdEvent> queue = new ArrayBlockingQueue<AdEvent>(20000);

    @Autowired
    MessagePublisher kafkaSinkSender;

    @Autowired
    OffsetSender offsetSender;

    // maintain a map of adId to count of clicks
    private WindowState window1 = new WindowState();
    private WindowState window2 = new WindowState();

    private boolean useMap1 = true;
    WindowState currentWindow = useMap1 ? window1 : window2;
    WindowState prevWindow = !useMap1 ? window1 : window2; // this is the map that gets flushed

    private long currentMinute = 0;
    private long prevMinute = 0;

    // timestamp in minutes to its map/window mapping. no need. the loop know which minute its processing, when changes that val can be passed to flush

    public void enqueEvent(AdEvent event) {
        queue.add(event);
    }

    @PostConstruct
    public void startReducerTask() {
        logger.info("Starting reducer task");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while(true) {
                // fetch from queue and populate map
                if (!queue.isEmpty()) {
                    AdEvent event = queue.poll();
                    currentMinute = event.getTimestamp();

                    // if timestamp minute changes, switch the map and flush the other one. this check done for every AdEvent
                    if (currentMinute != prevMinute) {
                        // switch the map
                        switchMaps();

                        // flush the other map with prevMinute
                        flush(prevMinute);
                    }

                    currentWindow.getWindowMap().merge(event.getAdId(), 1, Integer::sum);
                    currentWindow.setWindowOffset(event.getKafkaOffset());

                    prevMinute = currentMinute;
                }
            }
        });

        // shutdown the executor when you're done
        // executor.shutdown();
    }

    private void switchMaps() {
        useMap1 = !useMap1;
        currentWindow = useMap1 ? window1 : window2;
        prevWindow = !useMap1 ? window1 : window2;
        logger.info("window switched");
        logger.info("currentWindow: " + currentWindow);
        logger.info("prevWindow to flush: " + prevWindow);
    }

    /*
     * asyncronous flush to kafkaSink
     */
    private void flush(long flushWindowMinute) {
        //make copy and run async send task on that
        WindowState toFlushWindowCopy = new WindowState(prevWindow);
        
        //imp - clear the map after making copy
        prevWindow.clearState();

        // Flush the map to the service asynchronously
        CompletableFuture.runAsync(() -> {
            toFlushWindowCopy.getWindowMap().forEach((key, value) -> {
                kafkaSinkForMap(key, value, flushWindowMinute);
            });
        }).thenRun(() -> {
            logger.info("Flushed map to kafkaSink");
            // send offset to mapper vis grpc
            offsetSender.sendOffset(toFlushWindowCopy.getWindowOffset());
        });        
    }

    public void kafkaSinkForMap(long adId, int count, long timestamp) {
        logger.info("Flushed : adId: " + adId + " count: " + count + " timestamp: " + timestamp);
        // send to sink kafka topic
        kafkaSinkSender.sendMessage(new AdClickCount(adId, count, timestamp));
    }
}
