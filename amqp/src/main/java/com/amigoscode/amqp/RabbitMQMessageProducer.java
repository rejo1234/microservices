package com.amigoscode.amqp;

import com.amigoscode.publisher.EventPublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
@Profile("rabbit")
public class RabbitMQMessageProducer implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE = "internal.exchange";
    private static final String ROUTING_KEY = "internal.notification.routing-key";

    @Override
    public void sendEvent(Object event, Integer customerId) {
        log.info("Publishing to {} using routingKey {}. Payload: {}", EXCHANGE, ROUTING_KEY, event);

        log.info("Using message converter: {}", rabbitTemplate.getMessageConverter().getClass().getName());

        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, event);

        log.info("Published to {} using routingKey {}. Payload: {}", EXCHANGE, ROUTING_KEY, event);
    }
}
