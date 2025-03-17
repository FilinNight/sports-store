package com.company.store.service.producer;

import com.company.store.dto.NotificationDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationProducer {

    private final String TOPIC_NOTIFICATION = "notification";
    private final KafkaTemplate<String, NotificationDto> kafkaTemplate;

    public void sendNotification(NotificationDto message) {
        kafkaTemplate.send(TOPIC_NOTIFICATION, message);
    }
}
