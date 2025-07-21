package com.amigoscode.kafka;

import com.amigoscode.notification.NotificationRequest;
import com.amigoscode.publisher.EventPublisher;
import com.amigoscode.topics.Topics;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Primary
@Service
@Slf4j
@Profile("kafka")
public class KafkaProducerService implements EventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(String topic, String id, Object event) {
        try {
            String type = event.getClass().getSimpleName();
            ProducerRecord<String, Object> record = new ProducerRecord<>(topic, id, event);
            record.headers().add("type", type.getBytes(StandardCharsets.UTF_8));

            log.info("📤 Klasa NotificationRequest w producerze: {}", NotificationRequest.class.getName());
            logger.info("📤 Wysyłam event typu {} na topic '{}': {}", type, topic, event);
            kafkaTemplate.send(record); // ✅ teraz nagłówek też leci!
        } catch (Exception e) {
            logger.error("❌ Błąd serializacji eventu do JSON: {}", e.getMessage(), e);
        }
    }

    @Override
    public void sendEvent(Object event, Integer customerId) {
        log.info("✅ Wysyłam NotificationRequest na Kafkę: {}", event);
        publish(Topics.USER_TOPIC, customerId.toString(), event);
    }
}

