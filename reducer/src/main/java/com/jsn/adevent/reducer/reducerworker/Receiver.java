package com.jsn.adevent.reducer.reducerworker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.grpc.stub.StreamObserver;
import com.jsn.adevent.reducer.reducerworker.Reducer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.jsn.adevent.reducer.grpc.*;
import com.jsn.adevent.reducer.grpc.AdClickEventServiceGrpc.AdClickEventServiceImplBase;
import com.jsn.adevent.reducer.grpc.AdClickEventServiceGrpc.*;
import com.jsn.adevent.reducer.model.AdClickEvent;

@Component
public class Receiver extends AdClickEventServiceImplBase {
    Logger logger = LogManager.getLogger(Receiver.class);

    @Autowired
    Reducer reducer;
    
    /*
     * grpc service - receiving events from mapper
     */
    @Override
    public void sendEvent(AdEventMap request, StreamObserver<Empty> responseObserver) {
        //super.sendEvent(request, responseObserver);
        logger.info("Reducer: received event : "+request.getEvents(0).getAdId()+" "+request.getEvents(0).getTimestamp() + " offset : "+request.getEvents(0).getKafkaOffset());
        reducer.enqueEvent(request.getEvents(0));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
