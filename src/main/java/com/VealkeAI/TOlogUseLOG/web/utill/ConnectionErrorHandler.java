package com.VealkeAI.TOlogUseLOG.web.utill;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class ConnectionErrorHandler {

    @Value("${bot.url.address}")
    private String address;

    @Value("${bot.url.port}")
    private int port;

    private final Logger logger = LoggerFactory.getLogger(ConnectionErrorHandler.class);
    public static final AtomicBoolean isWork = new AtomicBoolean(false);


    //300000
    @Scheduled(fixedDelay = 5000)
    private void isConnectionPossible() {
        try(Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(address, port), 2000);
            isWork.set(true);
            logger.info("Connection successful");
        } catch (IOException e){
            isWork.set(false);
            logger.error("Cannot connect to server: {}", e.getMessage());
        }
    }
    @PostConstruct
    private void init(){
        isConnectionPossible();
    }
}
