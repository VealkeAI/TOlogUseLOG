package com.VealkeAI.TOlogUseLOG.service.impl;

import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import com.VealkeAI.TOlogUseLOG.service.scheduler.SendMessageJob;
import lombok.AllArgsConstructor;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.ScheduleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class SchedulerService {

    private final Logger logger = LoggerFactory.getLogger(SchedulerService.class);
    private final SendMessageJob sendMessageJob;


    public void createJob(Long id, Instant deadline , Integer shift) {

        try {
            BackgroundJob.schedule(deadline.minus(shift, ChronoUnit.HOURS),
                    () -> sendMessageJob.execute(id));

            logger.info("created job with id: {}", id);
        } catch (ScheduleException e) {
            logger.error(e.getMessage());
        }
    }
}
