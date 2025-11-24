package com.VealkeAI.TOlogUseLOG.service.scheduler;

import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import com.VealkeAI.TOlogUseLOG.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jobrunr.jobs.lambdas.JobRequest;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SendMessageJob implements JobRequestHandler<JobRequest> {

    private final Logger logger = LoggerFactory.getLogger(SendMessageJob.class);
    private final RestTemplate restTemple;

    @Value("${bot.url.post}")
    private String url;

    @Override
    public void run(JobRequest jobRequest) throws Exception {
//
//        job
//
//        Map<String, String> requestBody = Map.of(
//                "userId", tgId,
//                "name", name,
//                "description", description
//        );
//
//        logger.info("executing job");
//        restTemple.postForObject(url, requestBody, String.class);
//
//        logger.info("done job for user: {}", tgId);
    }
}
