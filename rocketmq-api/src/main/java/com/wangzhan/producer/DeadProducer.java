package com.wangzhan.producer;

import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author wangzhan
 * @version 1.0
 * @description 死信消息生产者
 * @date 2023/7/31 20:44:03
 */
public class DeadProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(MqConstant.PRODUCER_GROUP);
        defaultMQProducer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        defaultMQProducer.start();

        Message message = new Message(MqConstant.STUDY_DEAD_TOPIC, "发送死信消息".getBytes());
        defaultMQProducer.send(message);

        defaultMQProducer.shutdown();
    }
}
