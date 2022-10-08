package com.weil.example;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @Name: PublishSample
 * @Description: 发布
 * @Author: weil
 * @Date: 2022-09-27 16:11
 * @Version: 1.0
 */
public class PublishSample {
    public static void main(String[] args) {

        String broker = "tcp://127.0.0.1:1883";
        String topic = "t1";
        String username = "admin";
        String password = "admin123";
        String clientId = "publish_client";
        String content = "Hello MQTT";
        int qos = 0;

        try {
            MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
            // 连接参数
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置用户名和密码
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(60);
            // 连接
            client.connect(options);
            // 设置回调
            client.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost: " + cause.getMessage());
                }

                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("topic: " + topic);
                    System.out.println("Qos: " + message.getQos());
                    System.out.println("message content: " + new String(message.getPayload()));

                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }

            });

            // 创建消息并设置 QoS
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            // 设置保存
            message.setRetained(false);
            // 发布消息
            client.publish(topic, message);
            // 关闭连接
            client.disconnect();
            // 关闭客户端
            client.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
