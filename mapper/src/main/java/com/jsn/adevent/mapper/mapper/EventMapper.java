package com.jsn.adevent.mapper.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jsn.adevent.mapper.model.AdClickEvent;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.jsn.adevent.mapper.grpc.*;
//import com.jsn.adevent.mapper.grpc.AdClickEventServiceGrpc.AdClickEventServiceBlockingStub;
import com.jsn.adevent.mapper.grpc.AdClickEventServiceGrpc.AdClickEventServiceBlockingStub;

/*
 * for now: forward as soon as you receive,
 * otherwise : in batch of 1 second or 5 seconds. to reduce n/w delay
 */
@Component
public class EventMapper {

    /*
     * In this example, AdClickEventServiceGrpc, AdClickEventRequest, and AdClickEventResponse are generated 
     * by the protoc compiler from your .proto file. You'll need to replace these with the actual classes generated for your service.

        Please note that this is a blocking (synchronous) call. If you want to make a non-blocking (asynchronous) call, 
        you can use AdClickEventServiceGrpc.newStub(channel) to get a non-blocking stub, 
        and then use the sendEvent method that takes a StreamObserver<AdClickEventResponse> as a second argument.

     */
    private final AdClickEventServiceBlockingStub blockingStub;

    //Map<Long, List<AdClickEvent>> mapOutput = new HashMap<>();

    //@Value(value = "${grpc-server.address}")
    String host= "localhost";
    //@Value(value = "${grpc.server.port}")
    int port = 10002;

    public EventMapper() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        blockingStub = AdClickEventServiceGrpc.newBlockingStub(channel);
    }

    /*
     * send via gRpc.
     * This object is grpcClient, since it is sending data to grpc server
     * TODO: make it async ?
     */
    public void sendEvent(AdClickEvent event) {
        AdEventMap request = AdEventMap.newBuilder()
            .addEvents(AdEvent.newBuilder()
            .setAdId(event.getAdId())
            .setTimestamp(event.getTimestamp())
            .setKafkaOffset(event.getKafkaOffset())
            .build())
            .build();
        blockingStub.sendEvent(request);
    }
}
