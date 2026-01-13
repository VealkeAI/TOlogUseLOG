package com.VealkeAI.TOlogUseLOG.repository;

import com.VealkeAI.TOlogUseLOG.entity.NotificationOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationOutboxRepository extends JpaRepository<NotificationOutboxEntity, Long> {
    @Query("""
            SELECT n FROM NotificationOutboxEntity n
                WHERE n.isLoaded = false
    """)
    List<NotificationOutboxEntity> getAllUnloadedTasks();
}
