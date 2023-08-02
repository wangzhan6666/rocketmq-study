package com.wangzhan.producer;

import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author wangzhan
 * @version 1.0
 * @description 单向消息生产者:这种方式主要用在不关心发送结果的场景，这种方式吞吐量很大，但是存在消息丢失的风险，例如日志信息的发送
 * @date 2023/7/30 21:44:06
 */
public class OneWayProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(MqConstant.PRODUCER_GROUP);
        defaultMQProducer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);

        defaultMQProducer.start();

        Message message = new Message(MqConstant.STUDY_ONEWAY_TOPIC, "发送单向消息".getBytes());

        defaultMQProducer.sendOneway(message);  // 发送单向消息

        defaultMQProducer.shutdown();
    }
}
