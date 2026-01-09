package com.VealkeAI.TOlogUseLOG.entity;

import com.VealkeAI.TOlogUseLOG.web.enums.PriorityStatus;
import com.VealkeAI.TOlogUseLOG.web.enums.State;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "tasks")
@AllArgsConstructor
public class TaskEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_time", nullable = false)
    private Instant creationTime;

    @Column(name = "deadline")
    private Instant deadline;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private PriorityStatus priority;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;

    public TaskEntity() {}

    public TaskEntity(Long id,
                      String name,
                      String description,
                      Instant deadline,
                      PriorityStatus priority,
                      State state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.state = state;
    }
}
