package com.jsn.jobmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jsn.jobmanager.kubernetes.ClusterCreator;

public class JobManagerMain {
    public static final Logger LOG = LoggerFactory.getLogger(JobManagerMain.class);
    public static void main(String[] args) {
        //int kafkaPartitionCount = 10;
        //int mappersPerPartition = 1;
        if (args.length < 2) {
            System.out.println("Please provide kafkaPartitionCount and mappersPerPartition as arguments.");
            System.exit(1);
        }
    
        int kafkaPartitionCount;
        int mappersPerPartition;
    
        try {
            kafkaPartitionCount = Integer.parseInt(args[0]);
            mappersPerPartition = Integer.parseInt(args[1]);

            ClusterCreator clusterCreator = ClusterCreator.getInstance();
            clusterCreator.createCluster(kafkaPartitionCount, mappersPerPartition);
            LOG.info("created cluster successfully");

        } catch (NumberFormatException e) {
            System.out.println("Both kafkaPartitionCount and mappersPerPartition should be integers.");
            System.exit(1);
        }
    }
}