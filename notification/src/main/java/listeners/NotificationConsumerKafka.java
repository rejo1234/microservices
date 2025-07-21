package listeners;

import com.amigoscode.notification.NotificationRequest;
import com.amigoscode.notification.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@AllArgsConstructor
@Slf4j
@Profile("kafka")
public class NotificationConsumerKafka {
    private final NotificationService notificationService;

    @KafkaListener(topics = "user-events", groupId = "my-app-group")
    public void consume(ConsumerRecord<String, String> record) {

        Header typeHeader = record.headers().lastHeader("type");
        log.info("otrzymano json {}", record.value());
        if (typeHeader == null) {
            log.warn("⚠️ Brak nagłówka 'type' – wiadomość pominięta");
            return;
        }
        String type = new String(typeHeader.value(), StandardCharsets.UTF_8);
        log.info("▶️ Otrzymano wiadomość typu '{}': {}", type, record.value());
        log.info("📥 Klasa NotificationRequest w listenerze: {}", NotificationRequest.class.getName());

        ObjectMapper mapper = new ObjectMapper();
        try {
            if (type.equals("NotificationRequest")) {
                System.out.println("pirntuje jsona " + record.value());
                NotificationRequest notificationRequest = mapper.readValue(record.value(), NotificationRequest.class);
                log.info("✅ Zdeserializowano NotificationRequest: {}", notificationRequest);
                notificationService.send(notificationRequest);
            } else {
                log.warn("❌ Nieznany typ wiadomości: {}", type);
            }
        } catch (Exception e) {
            log.error("❌ Błąd przy deserializacji JSON typu {}: {}", type, e.getMessage(), e);
        }
    }
}
