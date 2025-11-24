package com.VealkeAI.TOlogUseLOG.service.scheduler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jobrunr.jobs.lambdas.JobRequest;
import org.jobrunr.jobs.lambdas.JobRequestHandler;

@AllArgsConstructor
@Getter
public class JobData implements JobRequest {

    private String tgId;
    private String name;
    private String description;

    @Override
    public Class<? extends JobRequestHandler> getJobRequestHandler() {
        return SendMessageJob.class;
    }
}
