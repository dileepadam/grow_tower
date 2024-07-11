package lk.robotikka.growtowermonitoringservice.service;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttSubscriberService implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(MqttSubscriberService.class);

    @Autowired
    private MqttClient mqttClient;

    @Value("${mqtt.topic.mtrics}")
    private String metricsTopic;

    @Autowired
    private MqttService mqttService;

    @PostConstruct
    public void subscribeToTopics() throws MqttException {
        mqttClient.setCallback(this);
        mqttClient.subscribe(metricsTopic);
//        mqttClient.subscribe(alertTopic);
    }

    @Override
    public void connectionLost(Throwable cause) {
        logger.debug("Connection lost: {} ", cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());
        logger.debug("Message received on topic " + topic + ": " + payload);

        System.out.println("Message received on topic " + topic + ":" + payload);

        switch (topic) {
            case "grow_tower_data":
                mqttService.getGrowTowerMetrics(payload);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + topic);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
