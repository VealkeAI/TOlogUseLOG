package com.VealkeAI.TOlogUseLOG.config;

import com.VealkeAI.TOlogUseLOG.service.schedulerJobs.SendMessageJob;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return  new RestTemplate();
    }

    /*
     the serialization method is obsolete in kafka 4.0.0 and will be changed later
     */
    @Bean
    public ProducerFactory<String, SendMessageJob> producerFactory(ObjectMapper objectMapper) {
        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        JsonSerializer<SendMessageJob> serializer = new JsonSerializer<>(objectMapper);
        serializer.setAddTypeInfo(false);

        return new DefaultKafkaProducerFactory<>(
                configProperties,
                new StringSerializer(),
                serializer
        );
    }

    @Bean
    public KafkaTemplate<String, SendMessageJob> kafkaTemplate(
            ProducerFactory<String, SendMessageJob> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
