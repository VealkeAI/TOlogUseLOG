package com.VealkeAI.TOlogUseLOG.service.scheduler;

import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import com.VealkeAI.TOlogUseLOG.web.Util;
import lombok.AllArgsConstructor;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
@AllArgsConstructor
public class RegisterJob {

    public static JobDetail buildJobDetail(TaskEntity task){
        JobDataMap data = new JobDataMap();
        data.put("userId", task.getUser().getTgId());
        data.put("name", task.getName());
        data.put("description", task.getDescription());

        return JobBuilder
                .newJob(SendMessageJob.class)
                .withIdentity(task.getName(),
                        task.getUser().getTgId().toString())
                .usingJobData(data)
                .storeDurably()
                .build();
    }

    public static Trigger buildTrigger(TaskEntity task) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(task.getName(),
                        task.getUser().getTgId().toString())
                .withDescription("Trigger for task " + task.getId())
                .startAt(
                        Util.toDate(
                                task.getDeadline()
                                        .minus(task.getUser().getShiftUTC(), ChronoUnit.HOURS)
                        ))
                .build();
    }

}
