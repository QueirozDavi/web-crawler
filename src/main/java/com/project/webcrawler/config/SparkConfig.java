package com.project.webcrawler.config;

import org.apache.spark.SparkConf;

public class SparkConfig {


    public static SparkConf getSparkConf() {
        return new SparkConf()
                .setAppName("NB Classifier")
                .setMaster("spark://192.168.56.1:7077")
                .setJars(
                        new String[]{"/smart-crawler/target/smart-crawler-0.0.1-SNAPSHOT.jar"})
                .set("spark.akka.frameSize", "20");
    }
}
