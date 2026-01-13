package com.VealkeAI.TOlogUseLOG.repository;

import com.VealkeAI.TOlogUseLOG.entity.NotificationOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationOutboxRepository extends JpaRepository<NotificationOutboxEntity, Long> {
}
