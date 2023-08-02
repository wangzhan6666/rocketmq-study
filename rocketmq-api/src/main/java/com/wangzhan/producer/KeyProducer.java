package com.wangzhan.producer;

import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author wangzhan
 * @version 1.0
 * @description key消息生产者
 * @date 2023/7/31 16:08:45
 */
public class KeyProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(MqConstant.PRODUCER_GROUP);
        defaultMQProducer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        defaultMQProducer.start();

        Message message = new Message(MqConstant.STUDY_KEY_TOPIC, "tagA", "唯一key", "发送key消息".getBytes());
        defaultMQProducer.send(message);

        defaultMQProducer.shutdown();
    }
}
