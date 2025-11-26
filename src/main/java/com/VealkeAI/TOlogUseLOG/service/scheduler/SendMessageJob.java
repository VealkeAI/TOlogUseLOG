package com.VealkeAI.TOlogUseLOG.service.scheduler;

import com.VealkeAI.TOlogUseLOG.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SendMessageJob {

    private final Logger logger = LoggerFactory.getLogger(SendMessageJob.class);
    private final RestTemplate restTemple;
    private final TaskRepository repository;

    @Value("${bot.url.post}")
    private String url;

    public void execute(Long taskId) {

        var task = repository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Not found task by id " + taskId));

        Map<String, String> requestBody = Map.of(
                "userId", task.getUser().getTgId().toString(),
                "name", task.getName(),
                "description", task.getDescription()
        );

        restTemple.postForObject(url, requestBody, String.class);

        logger.info("done job with id: {}", taskId);
    }
}
