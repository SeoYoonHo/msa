package com.minibank.transfer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.minibank.transfer.domain.entity.TransferHistory;
import com.minibank.transfer.domain.entity.TransferLimit;


@Configuration
public class KafkaProducerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    
    @Bean(name="transferProducerFactory")
    public ProducerFactory<String, TransferHistory> transferProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name="transferKafkaTemplate")
    public KafkaTemplate<String, TransferHistory> transferKafkaTemplate() {
        return new KafkaTemplate<>(transferProducerFactory());
    }
    
    @Bean(name="transferLimitProducerFactory")
    public ProducerFactory<String, TransferLimit> transferLimitProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name="transferLimitKafkaTemplate")
    public KafkaTemplate<String, TransferLimit> transferLimitKafkaTemplate() {
        return new KafkaTemplate<>(transferLimitProducerFactory());
    }

    
    //TODO: lab4 추가실습

    //TODO: lab4 추가실습
    
}