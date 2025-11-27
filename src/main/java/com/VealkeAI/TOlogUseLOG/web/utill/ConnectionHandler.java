package com.VealkeAI.TOlogUseLOG.web.utill;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.server.BackgroundJobServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class ConnectionHandler {

    @Value("${bot.url.address}")
    private String address;

    @Value("${bot.url.port}")
    private int port;

    private final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);
    private final BackgroundJobServer jobServer;
    private final AtomicBoolean isSchedulerStarted = new AtomicBoolean(true);
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    private void startChecking() {
        scheduledExecutor.scheduleWithFixedDelay(this::isConnected, 0, 5, TimeUnit.SECONDS);
    }

    //TODO: настороить отключение планировика
    private void isConnected() {
        boolean isConnection = isConnectionAvailable();
        if (isConnection && !isSchedulerStarted.get()) {
            jobServer.start();
            isSchedulerStarted.set(true);
            logger.info("Connection successful, scheduler started");
        }
        else if (!isConnection && isSchedulerStarted.get()) {
            jobServer.stop();
            isSchedulerStarted.set(false);
            logger.error("Cannot connect to server, scheduler stopped");
        }
    }

    private boolean isConnectionAvailable() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(address, port), 2000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
