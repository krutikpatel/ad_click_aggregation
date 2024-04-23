package com.jsn.jobmanager.clusterconfig;

public class ClusterConfig {
    // create a class called Mapper
    public class Mapper {

        // create a method called map
        public void map() {
            // print "Mapping"
            System.out.println("Mapping");
        }
    }
    
    // create a class called Reducer
    public class Reducer {
        // create a method called reduce
        public void reduce() {
            // print "Reducing"
            System.out.println("Reducing");
        }
    }

    int numPatitions;
    

}
