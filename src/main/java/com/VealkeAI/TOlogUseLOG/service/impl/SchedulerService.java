package com.VealkeAI.TOlogUseLOG.service.impl;

import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import com.VealkeAI.TOlogUseLOG.service.scheduler.JobData;
import com.VealkeAI.TOlogUseLOG.service.scheduler.SendMessageJob;
import lombok.AllArgsConstructor;
import org.jobrunr.scheduling.JobBuilder;
import org.jobrunr.scheduling.JobRequestScheduler;
import org.jobrunr.scheduling.ScheduleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

import static org.jobrunr.scheduling.JobBuilder.aJob;

@Service
@AllArgsConstructor
public class SchedulerService {

    private final Logger logger = LoggerFactory.getLogger(SchedulerService.class);
    private final SendMessageJob sendMessageJob;
    private final JobRequestScheduler requestScheduler;

    public void createJob(TaskEntity task, Integer shift) {

        var tgId = task.getUser().getTgId().toString();
        var name = task.getName();
        var description = task.getDescription();

        try {
            requestScheduler.create(aJob()
                    .withJobRequest(new JobData(tgId, name, description))
                    .scheduleAt(task.getDeadline().minus(shift, ChronoUnit.HOURS))
            );

            logger.info("created job for user with id: {}", task.getId());
        } catch (ScheduleException e) {
            logger.error(e.getMessage());
        }
    }
}
