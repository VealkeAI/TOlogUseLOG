package com.VealkeAI.TOlogUseLOG.service.impl;

import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import com.VealkeAI.TOlogUseLOG.service.scheduler.SendMessageJob;
import lombok.AllArgsConstructor;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.ScheduleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class SchedulerService {

    private final Logger logger = LoggerFactory.getLogger(SchedulerService.class);
    private final SendMessageJob sendMessageJob;


    public void createJob(TaskEntity task, Integer shift) {

        var tgId = task.getUser().getTgId().toString();
        var name = task.getName();
        var description = task.getDescription();

        try {
            BackgroundJob.schedule(task.getDeadline().minus(shift, ChronoUnit.HOURS),
                    () -> sendMessageJob.execute(tgId, name, description));

            logger.info("created job for user with id: {}", task.getId());
        } catch (ScheduleException e) {
            logger.error(e.getMessage());
        }
    }
}
