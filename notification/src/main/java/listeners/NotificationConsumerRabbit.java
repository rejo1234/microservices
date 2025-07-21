package listeners;

import com.amigoscode.notification.NotificationRequest;
import com.amigoscode.notification.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@AllArgsConstructor
@Slf4j
@Profile("rabbit")
public class NotificationConsumerRabbit {
    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consumer(Message message) {
        String typeId = (String) message.getMessageProperties().getHeaders().get("__TypeId__");
        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("📨 Otrzymano wiadomość typu: {}", typeId);

        try {
            ObjectMapper mapper = new ObjectMapper();

            if (typeId.equals("com.amigoscode.notification.NotificationRequest")) {
                NotificationRequest notificationRequest = mapper.readValue(json, NotificationRequest.class);
                notificationService.send(notificationRequest);
                log.warn("❓ Wysłany typ wiadomości: {}", typeId);
            } else {
                log.warn("❓ Nieznany typ wiadomości: {}", typeId);
            }
        } catch (Exception e) {
            log.error("❌ Błąd deserializacji wiadomości", e);
        }
    }
}
