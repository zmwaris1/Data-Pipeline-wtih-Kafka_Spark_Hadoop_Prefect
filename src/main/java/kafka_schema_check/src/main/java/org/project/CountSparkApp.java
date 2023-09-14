package org.project;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.api.java.function.ForeachFunction;

import java.util.Iterator;

public class CountSparkApp {
    
  public static void main(String[] args) {
    String logFile = "/media/zmwaris/mydrive/data-folder/Project-1/Data-Pipeline-wtih-Kafka_Spark_Hadoop_Prefect/src/main/java/someFile.txt"; // Should be some file on your system
    SparkSession spark = SparkSession.builder().master("spark://zmwaris-Predator-PH315-51:7077").appName("Simple Application").getOrCreate();
    Dataset<String> logData = spark.read().textFile(logFile);
    logData.show();
    System.out.println(logData.count());
    long row = logData.count();
    Iterator<String> iter = logData.toLocalIterator();

    int countA = 0;

    while (iter.hasNext()) { // i want a condition here to iterate only over the 20 first lines of my CSV
      String item = iter.next();
//      System.out.println(item);
      if (item.equals("a")) {
        countA = countA+1;
      }
    }
    System.out.println(countA);
//    for(int i=0; i<=row; i++) {
//      System.out.println(logData.collect()[i]);
//    }
//    logData.foreach((ForeachFunction<Row>) row -> System.out.println(row));
//    while (!logData.isEmpty()) {
//      System.out.println(logData); //gets the first column's rows.
//    }

//    long numAs = logData.filter((org.apache.spark.api.java.function.FilterFunction<String>)s -> s.contains("a")).count();
//    long numBs = logData.filter((org.apache.spark.api.java.function.FilterFunction<String>)s -> s.contains("b")).count();

//    System.out.println("Lines with a: " + numAs + "with b" + numBs);

    spark.stop();
  }
}    
