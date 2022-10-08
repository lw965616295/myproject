package com.weil.example2;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Scanner;

/**
 * @Name: PublishSample2
 * @Description:
 * @Author: weil
 * @Date: 2022-09-27 16:44
 * @Version: 1.0
 */
public class PublishSample2 {
    public static final String HOST = "tcp://localhost:1883";
    private String ServiceID = "publish_01";
    private String topic;
    private MqttClient client;
    private MqttTopic mqttTopic;
    private MqttConnectOptions options;
    private String user = "admin";
    private String password = "admin123";

    private MqttMessage message;

    public void ServiceMQTT() throws MqttException {
        //创建连接
        client = new MqttClient(HOST,ServiceID,new MemoryPersistence());
        options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setKeepAliveInterval(20);
        options.setConnectionTimeout(50);
        options.setUserName(user);
        options.setPassword(password.toCharArray());
        message = new MqttMessage();
    }

    public void getConnect(){
        try {
            client.setCallback(new CallBack());
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(MqttTopic topic, MqttMessage message) throws MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("消息推送的状态--->"+token.isComplete());
    }


    public static void main(String[] args) throws MqttException {
        PublishSample2 service = new PublishSample2();
        service.ServiceMQTT();
        service.getConnect();
        Scanner input = new Scanner(System.in);
        while (true){
            System.out.print("请输入消息的主题：");
            service.topic = input.next();

            System.out.print("请输入消息的内容：");
            String messageVal = input.next();



            service.message.setQos(1);
            // 消息不保留，不然每次订阅者重启连接订阅该主题都会收到最后一次的信息
            service.message.setRetained(false);
            service.message.setPayload(messageVal.getBytes());

            service.mqttTopic  = service.client.getTopic(service.topic);
            service.publish(service.mqttTopic,service.message);
            System.out.println("消息的保持状态："+service.message.isRetained());
        }

    }
}
