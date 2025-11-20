package com.VealkeAI.TOlogUseLOG.service.impl;

import com.VealkeAI.TOlogUseLOG.DTO.TaskDTO;
import com.VealkeAI.TOlogUseLOG.service.scheduler.RegisterJob;
import lombok.AllArgsConstructor;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SchedulerService {

    private final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    private final Scheduler scheduler;

    public void createJob(TaskDTO task) {
        try {
            var detail = RegisterJob.buildJobDetail(task);
            var trigger = RegisterJob.buildTrigger(task);

            scheduler.scheduleJob(detail, trigger);

            logger.info("created job for user with id: {}", task.id());
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
        }
    }
}
