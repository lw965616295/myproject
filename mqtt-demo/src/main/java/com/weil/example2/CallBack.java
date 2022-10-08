package com.weil.example2;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @Name: CallBack
 * @Description:
 * @Author: weil
 * @Date: 2022-09-27 16:46
 * @Version: 1.0
 */
public class CallBack implements MqttCallback {
    @Override
    public void connectionLost(Throwable throwable) {
        //连接断掉会执行到这里
        System.out.println("连接以断，请重新连接！！！");
    }
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        //subscribe后会执行到这里
        System.out.println("消息的主题是："+s);
        System.out.println("消息的Qos是："+mqttMessage.getQos());
        System.out.println("消息的ID是："+mqttMessage.getId());
        System.out.println("消息的内容是："+new String(mqttMessage.getPayload()));
    }
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        //publish可以执行到这里
        System.out.println("This is deliveryComplete method----->"+iMqttDeliveryToken.isComplete());
    }
}
