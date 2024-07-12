package lk.robotikka.growtowermonitoringservice.service.impl;


import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lk.robotikka.growtowermonitoringservice.domain.request.NotificationRequest;
import lk.robotikka.growtowermonitoringservice.domain.request.SendDataPushRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Service
public class FCMService {

    private Logger logger = LoggerFactory.getLogger(FCMService.class);

    @Async
    public void sendMessageToToken(NotificationRequest request) throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to token. Device token: " + request.getToken() + ", " + response + " msg " + jsonOutput);
    }

    @Async
    public void sendDataToToken(SendDataPushRequest request) throws InterruptedException, ExecutionException {
        Message message = Message.builder()
                .setToken(request.getToken())
                .putAllData(request.getData())
                .build();

        String response = sendAndGetResponse(message);
        System.out.println("Sent data to token. Device token: " + request.getToken() + ", " + response + " msg " + request.getData());
        logger.info("Sent data to token. Device token: " + request.getToken() + ", " + response + " msg " + request.getData());
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }


    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private Message getPreconfiguredMessageToToken(NotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).setToken(request.getToken())
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(NotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        Notification notification = Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getBody())
                .build();
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(notification);
    }
}
