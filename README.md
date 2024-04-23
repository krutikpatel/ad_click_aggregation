# AdClickAggregator Project Overview

This project implements a data pipeline to count ad clicks for each ad per minute. The project uses the map-reduce paradigm for event aggregation.

## Assumptions

Ad click events are fed via Kafka to the aggregator, and the Kafka topic is partitioned by `adId`.

## Architecture

Insert diagram here

### Submodules

#### Generator

This module simulates the ad click event generation and sends them to Kafka to be consumed by the aggregator.

#### Mapper

The Mapper consumes ad-click events from Kafka. Each mapper consumes a particular partition. Thus, ad events are distributed among various mappers by `adIds`, and each mapper (and following reducer) is responsible for computing `adId` in a certain range.

The Mapper collects ad click events for every minute and then passes the data to the Reducer via gRPC. The application is containerized to run under a Kubernetes cluster.

#### Reducer

The Reducer consumes ad-click data from the Mapper(s) and reduces it to create an aggregated click count for each `adId` in its range. There is one reducer per Kafka partition. The Reducer also processes data per minute window and then sends aggregated data to the output Kafka topic.

Once the Reducer commits data to the output Kafka topic, the Kafka message offset is sent back to the Mapper, and the Mapper will commit the offset to Kafka, ensuring at least once guarantee.

The application is containerized to run under a Kubernetes cluster.

#### Job Manager

This module is responsible for creating the necessary Mapper and Reducer pods under the Kubernetes cluster as well as services to communicate among them. This provides an easy and manageable way to scale the aggregation pipeline.

## Usage/Example

### Setup Assumptions/Pre-requisites

- Kafka running at the default port
- Kubernetes cluster available

### Generator

Run this module to simulate ad-click events.

Command: 

```bash
java -jar generator-1.0.jar
```

### Jobmanager
    
-run this jar under node where kubernetes is running so that it can communicate with kubernetes server and create necessary resources for this project.
    
Command: 
```bash
    java -jar jobamanger-1.0.jar <no of kafkapartitions> <mapper instances needed per partition>
```