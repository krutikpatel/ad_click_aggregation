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
 * "Tumbling Window" of 1 minute
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

    //create lock
    private final Object lock = new Object();

    // maintain a map of adId to count of clicks
    private Map<Long, Integer> map1 = new HashMap<>();
    private Map<Long, Integer> map2 = new HashMap<>();
    private boolean useMap1 = true;
    Map<Long, Integer> currentMap = useMap1 ? map1 : map2;
    Map<Long, Integer> mapToFlush = !useMap1 ? map1 : map2;

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

                    //lock using the map u want to add to
                    //synchronized (currentMap) {
                        currentMap.merge(event.getAdId(), 1, Integer::sum);
                    //}
                    prevMinute = currentMinute;
                }
            }
        });

        // Don't forget to shutdown the executor when you're done
        // executor.shutdown();
    }

    private void switchMaps() {
        useMap1 = !useMap1;
        currentMap = useMap1 ? map1 : map2;
        mapToFlush = !useMap1 ? map1 : map2;
        logger.info("Switched to map: " + (useMap1 ? "map1" : "map2"));
        logger.info("currentMap: " + currentMap);
        logger.info("mapToFlush: " + mapToFlush);
    }

    /*
     * asyncronous flush to dummyservice
     */
    private void flush(long flushWindowMinute) {
        Map<Long, Integer> copyMapToFlush = new HashMap<>(mapToFlush);
        //may be make copy and run async send task on that

        //no need to lock since copy made - better than blocking the queue
        //lock using the map to flush
        //synchronized (mapToFlush) {
            // Flush the map to the service asynchronously
            CompletableFuture.runAsync(() -> {
                copyMapToFlush.forEach((key, value) -> {
                    dummySinkForMap(key, value, flushWindowMinute);
                });
            });
        //}
        
    }

    public void dummySinkForMap(long adId, int count, long timestamp) {
        logger.info("Flushed : adId: " + adId + " count: " + count + " timestamp: " + timestamp);
        // send to sink kafka topic
        kafkaSinkSender.sendMessage(new AdClickCount(adId, count, timestamp));
    }
}
