package com.jsn.jobmanager.kubernetes;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Config;

public class ClusterCreator {
    //slfj logger
    private static final Logger LOG = LoggerFactory.getLogger(ClusterCreator.class);

    ApiClient client;
    AppsV1Api api;

    //create singleton instance
    private static ClusterCreator instance = null;
    
    private ClusterCreator() {
        try {
            client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);
            api = new AppsV1Api();
        } catch (IOException e) {
            LOG.error("Error creating kubernetes client", e);
        }
    }
    public static ClusterCreator getInstance() {
        if(instance == null) {
            instance = new ClusterCreator();
        }
        
        return instance;
    }

    /*
     * -there will be only 1 reducer per partition
     * -no of mappers = no of partitions * mappersPerPartition
     */
    public void createCluster(int kafkaPartitionCount, int mappersPerPartition) {
        //create mappers
        createMapperDeployments(kafkaPartitionCount, mappersPerPartition);
        //create LB service
        createLbService(kafkaPartitionCount);
        //create mapper service
        createMapperToReducerService(kafkaPartitionCount);
        //create reducers
        createReducerDeployments(kafkaPartitionCount);
    }

    private void createMapperDeployments(int partitions, int mappersPerPartition) {
        for(int i = 1; i <= partitions; i++) {
            String mapperName = "mapper-" + i;
            V1Deployment deployment = new V1Deployment()
            .metadata(new V1ObjectMeta().name("deployment-"+mapperName))
            .spec(new V1DeploymentSpec()
                .replicas(mappersPerPartition)
                .selector(new V1LabelSelector().putMatchLabelsItem("app", mapperName))
                .template(new V1PodTemplateSpec()
                    .metadata(new V1ObjectMeta().putLabelsItem("app", mapperName))
                    .spec(new V1PodSpec()
                        .addContainersItem(new V1Container()
                            .name("mapper-container-"+i)
                            .image("<mapper-image>")
                            .addPortsItem(new V1ContainerPort().containerPort(8080))
                            .addEnvItem(new V1EnvVar().name("kafkapartitionindex").value(String.valueOf(i)))))));
            try {
                api.createNamespacedDeployment("default", deployment, null, null, null);
                LOG.info("Deployment created successfully.");
            } catch (ApiException e) {
                LOG.error("Exception when creating "+mapperName+" Deployment: " + e.getResponseBody());
            }
        }
    }

    private void createReducerDeployments(int partitions) {
        for(int i = 1; i <= partitions; i++) {
            V1Deployment deployment = new V1Deployment()
                .metadata(new V1ObjectMeta().name("deployment-reducer-"+i))
                .spec(new V1DeploymentSpec()
                    .replicas(1)
                    .selector(new V1LabelSelector().putMatchLabelsItem("app", "reducer-"+i))
                    .template(new V1PodTemplateSpec()
                        .metadata(new V1ObjectMeta().putLabelsItem("app", "reducer-"+i))
                        .spec(new V1PodSpec()
                            .addContainersItem(new V1Container()
                                .name("reducer-container-"+i)
                                .image("<your-image>")
                                .addPortsItem(new V1ContainerPort().containerPort(8080))
                                .addEnvItem(new V1EnvVar().name("kafkapartitionindex").value(String.valueOf(i)))))));

            try {
                api.createNamespacedDeployment("default", deployment, null, null, null);
                LOG.info("Deployment created successfully.");
            } catch (ApiException e) {
                LOG.error("Exception when creating reducer Deployment: " + e.getResponseBody());
            }
        }
    }

    //services
    // use this in reducer to connect as http://mapper-reducer-service-x:80
    private void createMapperToReducerService(int partitions) {
        for (int i = 1; i <= partitions; i++) {
            V1Service service = new V1Service()
            .metadata(new V1ObjectMeta().name("mapper-reducer-service-" + i))
            .spec(new V1ServiceSpec()
                .addPortsItem(new V1ServicePort().port(8080).targetPort(new IntOrString(8080)))
                .selector(Collections.singletonMap("app", "mapper-" + i)));
            try {
                CoreV1Api coreApi = new CoreV1Api();
                coreApi.createNamespacedService("default", service, null, null, null);
                System.out.println("Service created successfully.");
            } catch (ApiException e) {
                System.err.println("Exception when creating Service: " + e.getResponseBody());
            }
        }
    }

    private void createLbService(int partitions) {
        for (int i = 1; i <= partitions; i++) {
            V1Service service = new V1Service()
                .metadata(new V1ObjectMeta().name("lb-listener-service-" + i))
                .spec(new V1ServiceSpec()
                    .type("LoadBalancer")
                    .addPortsItem(new V1ServicePort().port(80).targetPort(new IntOrString(8080)))
                    .selector(Collections.singletonMap("app", "mapper-" + i)));
    
            try {
                CoreV1Api coreApi = new CoreV1Api();
                coreApi.createNamespacedService("default", service, null, null, null);
                LOG.info("Service " + i + " created successfully.");
            } catch (ApiException e) {
                LOG.error("Exception when creating LB Service " + i + ": " + e.getResponseBody());
            }
        }
    }
}
