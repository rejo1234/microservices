package com.amigoscode.amqp;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@AllArgsConstructor
@Profile("rabbit")
public class RabbitMQConfig {
    private final ConnectionFactory connectionFactory;

    @PostConstruct
    public void logBeanInit() {
        System.out.println("✅ RabbitMQConfig aktywowany! Zarejestrowano fabrykę rabbitListenerContainerFactory");
    }

    @Bean
    @Primary
    public MessageConverter jacksonConverter() {
        System.out.println("✅ Rejestruję Jackson2JsonMessageConverter");
        return new Jackson2JsonMessageConverter();
    }

    @Bean(name = "rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);
        return factory;
    }
}
