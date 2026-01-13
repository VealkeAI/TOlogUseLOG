package com.VealkeAI.TOlogUseLOG.service.impl;

import com.VealkeAI.TOlogUseLOG.repository.NotificationOutboxRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CheckingForNewNotifications {

    private final NotificationOutboxRepository outboxRepository;

    @Transactional
    public void sendNotification() {

    }
}
