package com.VealkeAI.TOlogUseLOG.service.scheduler;

import com.VealkeAI.TOlogUseLOG.DTO.TaskDTO;
import com.VealkeAI.TOlogUseLOG.web.Util;
import lombok.AllArgsConstructor;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegisterJob {

    public static JobDetail buildJobDetail(TaskDTO task){
        JobDataMap data = new JobDataMap();
        data.put("userId", task.userId());
        data.put("name", task.name());
        data.put("description", task.description());

        return JobBuilder
                .newJob(SendMessageJob.class)
                .withIdentity(task.name(), task.userId().toString())
                .usingJobData(data)
                .storeDurably()
                .build();
    }

    public static Trigger buildTrigger(TaskDTO task) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(task.name(), task.userId().toString())
                .withDescription("Trigger for task " + task.userId())
                .startAt(Util.toDate(task.deadline()))
                .build();
    }

}
