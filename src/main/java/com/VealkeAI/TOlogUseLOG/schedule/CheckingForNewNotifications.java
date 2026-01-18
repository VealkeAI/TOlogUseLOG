package com.VealkeAI.TOlogUseLOG.schedule;

import com.VealkeAI.TOlogUseLOG.repository.NotificationOutboxRepository;
import com.VealkeAI.TOlogUseLOG.service.impl.SchedulerService;
import com.VealkeAI.TOlogUseLOG.web.enums.NotificationSendState;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CheckingForNewNotifications {

    private final NotificationOutboxRepository outboxRepository;
    private final SchedulerService schedulerService;

    private final Logger logger = LoggerFactory.getLogger(CheckingForNewNotifications.class);

    @Transactional
    @Scheduled(fixedDelay = 30000)
    public void retryToSendNotification() {
        logger.info("retry started");
        List<Long> notificationIdList =
                outboxRepository.getNotificationByState(NotificationSendState.FAILED);

        for (Long id : notificationIdList) {
            schedulerService.createJob(id);
        }
    }
}
