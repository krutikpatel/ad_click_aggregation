package com.jsn.adevent.mapper.statemgmt;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

@Service
public class GrpcServer {

    Logger logger = LogManager.getLogger(GrpcServer.class);

    Server server;

    @Autowired
    OffsetReceiver receiver;

    // IMP this kind of value injection is NOT WORKING !
    //@Value(value = "${grpc-server.address}")
    //String host;// = "localhost";
    //@Value(value = "${grpc.server.port}")
    int port = 10003;   // knote: diff port than reducer's GrpcServer, because currently both are running on same machine. if runnning on diff machines/docker, then can use same port

    public GrpcServer() {
      
    }

    /*
     * start grpc server
     */
    @PostConstruct
    public void init() throws IOException, InterruptedException {
        try {
            server = ServerBuilder.forPort(port).
            addService(receiver).build(); //knote: v imp, add service here. otherwise client calling this method/service will get run-time error

            logger.info("Starting server...");
            server.start();
            logger.info("Server started!");
            server.awaitTermination();
        } catch (Exception e) {
            logger.error("Exception in grpc server init : "+e);
            //close server ?
        } finally {
            if (server != null) {
                server.shutdown();
            }
        }       
    }
}