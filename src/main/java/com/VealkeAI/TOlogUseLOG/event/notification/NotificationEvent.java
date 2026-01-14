package com.VealkeAI.TOlogUseLOG.event.notification;

import com.VealkeAI.TOlogUseLOG.service.impl.SchedulerService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class NotificationEvent{

    private final SchedulerService schedulerService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendNotificationJob(RegisterNotificationEvent event) {
        schedulerService.createJob(event.outboxId());
    }
}
