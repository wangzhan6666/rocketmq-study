package com.wangzhan.producer;

import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author wangzhan
 * @version 1.0
 * @description 标签消息生产者
 * @date 2023/7/31 10:01:55
 */
public class TagProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(MqConstant.PRODUCER_GROUP);
        defaultMQProducer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        defaultMQProducer.start();

        Message tagAMessage = new Message(MqConstant.STUDY_TAG_TOPIC, "tagA", "发送标签消息-tagA".getBytes());
        Message tagBCMessage = new Message(MqConstant.STUDY_TAG_TOPIC, "tagB", "发送标签消息-tagB".getBytes());

        defaultMQProducer.send(tagAMessage);
        defaultMQProducer.send(tagBCMessage);

        defaultMQProducer.shutdown();
    }
}
