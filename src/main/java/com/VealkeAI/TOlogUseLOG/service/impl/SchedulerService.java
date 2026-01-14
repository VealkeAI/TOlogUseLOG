package com.VealkeAI.TOlogUseLOG.service.impl;

import com.VealkeAI.TOlogUseLOG.entity.NotificationOutboxEntity;
import com.VealkeAI.TOlogUseLOG.service.schedulerJobs.SendMessageJob;
import com.VealkeAI.TOlogUseLOG.web.enums.NotificationSendState;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.jobrunr.scheduling.BackgroundJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class SchedulerService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(SchedulerService.class);
    private final SendMessageJob sendMessageJob;

    @Transactional
    public void createJob(Long NotificationOutboxId) {

        var notification = entityManager.find(NotificationOutboxEntity.class, NotificationOutboxId);

        try {
            BackgroundJob.schedule(
                    notification.getDeadline().minus(notification.getId(), ChronoUnit.HOURS),
                    () -> sendMessageJob.execute(notification.getTaskId())
            );

            notification.setState(NotificationSendState.SEND);

            logger.info("created job with id: {}", notification.getId());

        } catch (Exception e) {
            notification.setState(NotificationSendState.FAILED);

            logger.error(e.getMessage());
        }
    }
}
