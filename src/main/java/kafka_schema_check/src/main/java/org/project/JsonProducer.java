package org.project;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.project.data.CurrencyDAO;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class JsonProducer {
    public static Properties loadProperties(String fileName) throws IOException {
        final Properties props = new Properties();
        final FileInputStream input = new FileInputStream(fileName);
        props.load(input);
        input.close();
        return props;
    }

    public List<CurrencyDAO> getPrices(String filePath) throws IOException, CsvException {
        var priceStream = this.getClass().getResource(filePath);
        var reader = new CSVReader(new FileReader(priceStream.getFile()));
        reader.skip(1);
        return reader.readAll().stream().map(CurrencyDAO::new).collect(Collectors.toList());
    }

    public void publishPrice(List<CurrencyDAO> prices, KafkaProducer<String, CurrencyDAO> kafkaProducer) throws ExecutionException, InterruptedException {
        for(CurrencyDAO price: prices) {
            var record = kafkaProducer.send(new ProducerRecord<>("Kafka_Schema", String.valueOf(price.volume), price), (metadata, exception) -> {
                if(exception != null) {
                    System.out.println(exception.getMessage());
                }
            });
            System.out.println(record.get().offset());
            System.out.println(price.market_cap_USD);
            Thread.sleep(500);
        }
    }

    public static void main(String[] args) throws IOException, CsvException, ExecutionException, InterruptedException {
        var producer = new JsonProducer();
        Properties props;
        props = JsonProducer.loadProperties("/media/zmwaris1/mydrive/data-folder/Project-1/Data-Pipeline-wtih-Kafka_Spark_Hadoop_Prefect/src/main/java/kafka_schema_check/src/main/java/org/project/.config/producer_client.properties");
        KafkaProducer<String, CurrencyDAO> kafkaProducer = new KafkaProducer<>(props);
        var prices = producer.getPrices("/currency_daily_BTC_CNY.csv");
        producer.publishPrice(prices, kafkaProducer);
    }
}
