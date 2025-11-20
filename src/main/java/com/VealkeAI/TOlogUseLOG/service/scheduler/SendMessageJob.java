package com.VealkeAI.TOlogUseLOG.service.scheduler;

import com.VealkeAI.TOlogUseLOG.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SendMessageJob implements Job {

    // this shit needs tests

    private final Logger logger = LoggerFactory.getLogger(SendMessageJob.class);
    private final RestTemplate restTemple;
    private final UserRepository userRepository;

    @Value("${bot.url.post}")
    private String url;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap data = jobExecutionContext.getMergedJobDataMap();

        var userId = data.getLong("userId");
        var name = data.getString("name");
        var description = data.getString("description");

        var tgId = userRepository.findById(userId);

        Map<String, String> requestBody = Map.of(
                "tgId", tgId.toString(),
                "name", name,
                "description", description
        );

        restTemple.postForObject(url, requestBody, String.class);

        logger.info("done job for user: {}", userId);
    }
}
