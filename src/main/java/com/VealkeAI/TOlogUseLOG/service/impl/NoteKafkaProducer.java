package com.VealkeAI.TOlogUseLOG.service.impl;

import com.VealkeAI.TOlogUseLOG.DTO.MessageDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NoteKafkaProducer {

    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(NoteKafkaProducer.class);

    public void sendOrderToKafka(MessageDTO notification) {
        kafkaTemplate.send("notifications", notification.tgId(), notification);

        logger.info("sent message to kafka");
    }
}
