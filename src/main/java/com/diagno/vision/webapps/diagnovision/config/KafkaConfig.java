package com.diagno.vision.webapps.diagnovision.config;

import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<Integer, byte[]> producerFactory(KafkaProperties kafkaProperties){
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties(), new IntegerSerializer(), new ByteArraySerializer());
    }

    @Bean
    public KafkaTemplate<Integer, byte[]> kafkaTemplate(KafkaProperties kafkaProperties){
        return new KafkaTemplate<>(producerFactory(kafkaProperties));
    }
}
