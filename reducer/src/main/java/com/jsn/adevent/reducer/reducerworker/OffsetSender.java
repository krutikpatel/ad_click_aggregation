package com.jsn.adevent.reducer.reducerworker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jsn.adevent.reducer.grpc.*;
import com.jsn.adevent.reducer.grpc.OffsetServiceGrpc.OffsetServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Component
public class OffsetSender {
    Logger logger = LogManager.getLogger(OffsetSender.class);

    private final OffsetServiceBlockingStub blockingStub;

    //@Value(value = "${grpc-server.address}")
    String host= "localhost";
    //@Value(value = "${grpc.server.port}")
    int port = 10003;   //knote: same as GrpcServer in mapper

    public OffsetSender() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        blockingStub = OffsetServiceGrpc.newBlockingStub(channel);
    }

    public void sendOffset(long offset) {
        logger.info("[sendOffset] sending offset : "+offset);
        Offset request = Offset.newBuilder()
            .setOffset(offset)
            .build();
        blockingStub.sendOffset(request);
    }
}
