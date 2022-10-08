package com.weil.example2;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 * @Name: SubscribeSample2
 * @Description:
 * @Author: weil
 * @Date: 2022-09-27 16:51
 * @Version: 1.0
 */
public class SubscribeSample2 {
    public static final String HOST = "tcp://localhost:1883";
    private static final String clientID = "subscribe_"+ new Date().getTime();
    private String TOPIC;
    private MqttClient client;
    private MqttConnectOptions options;
    private String user = "admin";
    private String password = "admin123";


    public void clientStart(){
        try {
            client = new MqttClient(HOST,clientID,new MemoryPersistence());
            options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setKeepAliveInterval(10);
            options.setConnectionTimeout(50);
            options.setUserName(user);
            options.setPassword(password.toCharArray());
            client.setCallback(new CallBack());
            Scanner input = new Scanner(System.in);
            System.out.print("请输入订阅的主题：");
            TOPIC = input.next();
            MqttTopic topic = client.getTopic(TOPIC);
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
//            options.setWill(topic,"close".getBytes(),1,true);
            client.connect(options);
            int[] Qos = {1};
            String[] topic1 = {TOPIC};
            client.subscribe(topic1,Qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MqttException {
        SubscribeSample2 clientMQTT = new SubscribeSample2();
        clientMQTT.clientStart();
    }
}
