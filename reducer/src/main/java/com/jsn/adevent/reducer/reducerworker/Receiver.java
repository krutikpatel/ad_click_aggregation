package com.jsn.adevent.reducer.reducerworker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.grpc.stub.StreamObserver;
import com.jsn.adevent.reducer.reducerworker.Reducer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.jsn.adevent.reducer.grpc.Empty;
import com.jsn.adevent.reducer.grpc.*;
import com.jsn.adevent.reducer.grpc.AdClickEventServiceGrpc.AdClickEventServiceImplBase;
import com.jsn.adevent.reducer.grpc.AdClickEventServiceGrpc.*;
import com.jsn.adevent.reducer.model.AdClickEvent;

@Component
public class Receiver extends AdClickEventServiceImplBase {

    @Autowired
    Reducer reducer;
    
    /*
     * grpc service - receiving events from mapper
     */
    @Override
    public void sendEvent(AdEventMap request, StreamObserver<Empty> responseObserver) {
        //super.sendEvent(request, responseObserver);
        System.out.println("Reducer: received event : "+request.getEvents(0).getAdId()+" "+request.getEvents(0).getTimestamp());
        reducer.enqueEvent(request.getEvents(0));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
