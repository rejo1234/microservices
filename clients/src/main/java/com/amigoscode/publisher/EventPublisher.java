package com.amigoscode.publisher;

public interface EventPublisher {
    void sendEvent(Object event, Integer id);
}
