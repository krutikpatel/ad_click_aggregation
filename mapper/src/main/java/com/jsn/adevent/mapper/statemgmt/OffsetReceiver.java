package com.jsn.adevent.mapper.statemgmt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsn.adevent.mapper.grpc.*;
import com.jsn.adevent.mapper.grpc.OffsetServiceGrpc.OffsetServiceImplBase;
import com.jsn.adevent.mapper.kafka.EventConsumer;

import io.grpc.stub.StreamObserver;

@Component
public class OffsetReceiver extends OffsetServiceImplBase{
    Logger logger = LogManager.getLogger(OffsetReceiver.class);

    @Autowired
    EventConsumer eventConsumer;

    /*
     * grpc service - receiving offset from reducer
     */
    @Override
    public void sendOffset(Offset request, StreamObserver<Empty> responseObserver) {
        logger.info("[OffsetReceiver]: received offset : "+request.getOffset());

        //TODO take appropriate action with the offset
        eventConsumer.commitOffset(request.getOffset());
        
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
