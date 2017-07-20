package streams;

import config.ConfigurationManager;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import java.util.ArrayList;
import java.util.Properties;

public class XdrlocEnrichStream {

    public static void main(String[] args) {
        Properties streamsConfiguration = new Properties();

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "Enrich-Stream-1");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, ConfigurationManager.getInstance().kafkaAddress);
        streamsConfiguration.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, ConfigurationManager.getInstance().zookeeperAddress);
        streamsConfiguration.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        streamsConfiguration.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 600000);

        Serde<String> stringSerde = Serdes.String();
        final Serde<GenericRecord> genericRecordSerde = new GenericAvroSerde();

        final KStreamBuilder builder = new KStreamBuilder();
        final KStream<String, String> input = builder.stream(stringSerde, stringSerde, "enrich-1");

        final KStream<String, String> streamOutput = input.flatMapValues(item -> {
            ArrayList result = new ArrayList();
            try {

                return result;

            } catch (Exception ex) {
                result = new ArrayList();
            }

            return result;
        });

        streamOutput.print();

        final KafkaStreams streams = new KafkaStreams(builder, streamsConfiguration);
        streams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
