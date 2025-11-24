package com.VealkeAI.TOlogUseLOG.service.scheduler;

import lombok.RequiredArgsConstructor;
import org.jobrunr.jobs.lambdas.JobRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class Sd {
    private final Logger logger = LoggerFactory.getLogger(Sd.class);
    private final RestTemplate restTemple;

    @Value("${bot.url.post}")
    private String url;

    public void execute(String tgId,String name, String description) {

        Map<String, String> requestBody = Map.of(
                "userId", tgId,
                "name", name,
                "description", description
        );

        logger.info("executing job");
        restTemple.postForObject(url, requestBody, String.class);

        logger.info("done job for user: {}", tgId);
    }
}
