package com.VealkeAI.TOlogUseLOG.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "notification_outbox")
@AllArgsConstructor
public class NotificationOutboxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long taskId;
    private Instant deadline;
    private Integer shift;
    private Boolean isLoaded = false;

    public NotificationOutboxEntity() {}

    public NotificationOutboxEntity(Long taskId, Instant deadline, Integer shift) {
        this.taskId = taskId;
        this.deadline = deadline;
        this.shift = shift;
    }
}
