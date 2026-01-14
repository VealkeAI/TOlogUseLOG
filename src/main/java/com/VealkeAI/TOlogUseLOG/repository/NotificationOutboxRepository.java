package com.VealkeAI.TOlogUseLOG.repository;

import com.VealkeAI.TOlogUseLOG.entity.NotificationOutboxEntity;
import com.VealkeAI.TOlogUseLOG.web.enums.NotificationSendState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationOutboxRepository extends JpaRepository<NotificationOutboxEntity, Long> {
    @Query("""
        SELECT n.id FROM NotificationOutboxEntity n
                WHERE n.state = :state
        """)
    List<Long> getNotificationByState(@Param("state")NotificationSendState state);
}
