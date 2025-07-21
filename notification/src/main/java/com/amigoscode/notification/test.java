package com.amigoscode.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("rabbit")
@Slf4j
public class test implements InitializingBean {
    private final RabbitListenerContainerFactory<?> rabbitListenerContainerFactory;

    @Override
    public void afterPropertiesSet() {
        var factory = (SimpleRabbitListenerContainerFactory) rabbitListenerContainerFactory;
        log.info("📦 AKTYWNY KONWERTER: {}", factory.getClass().getName());
    }
}
