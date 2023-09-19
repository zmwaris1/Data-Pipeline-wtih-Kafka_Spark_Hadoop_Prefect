package org.project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.project.data.CurrencyDAO;

import io.confluent.kafka.serializers.KafkaJsonDeserializerConfig;

public class JsonConsumer {
    
    // private KafkaConsumer<String, CurrencyDAO> consumer;
    public static Properties loadProperties(String fileName) throws IOException {
        final Properties props = new Properties();
        final FileInputStream input = new FileInputStream(fileName);
        props.load(input);
        props.put(KafkaJsonDeserializerConfig.JSON_VALUE_TYPE, CurrencyDAO.class);
        input.close();
        return props;
    }

    public void ConsumerApp(KafkaConsumer<String, CurrencyDAO> consumer) {
        System.out.println("Consuming from Kafka");
        var results = consumer.poll(Duration.of(1, ChronoUnit.SECONDS));
        var i = 0;
        do {
            for(ConsumerRecord<String, CurrencyDAO> result: results) {
                System.out.println(result.key()+"-->"+result.toString());
            }
            results = consumer.poll(Duration.of(1, ChronoUnit.SECONDS));

            System.out.println("Results--> "+results.count());
        }
        while(!results.isEmpty() || i < 10);
    }
    public static void main(String[] args) throws IOException {
        JsonConsumer consumer = new JsonConsumer();
        Properties props = JsonConsumer.loadProperties("/media/zmwaris1/mydrive/data-folder/Project-1/Data-Pipeline-wtih-Kafka_Spark_Hadoop_Prefect/src/main/java/kafka_schema_check/src/main/java/org/project/.config/consumer_client.properties");
        KafkaConsumer<String, CurrencyDAO> consumer2 = new KafkaConsumer<String, CurrencyDAO>(props);
        consumer2.subscribe(List.of("Kafka_Schema"));
        consumer.ConsumerApp(consumer2);

    }
}
