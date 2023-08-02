package com.wangzhan.producer;

import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangzhan
 * @version 1.0
 * @description 批量消息生产者
 * @date 2023/7/31 08:32:28
 */
public class BatchProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(MqConstant.PRODUCER_GROUP);
        defaultMQProducer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        defaultMQProducer.start();

        // 准备消息
        List<Message> list = Arrays.asList(
                new Message(MqConstant.STUDY_BATCH_TOPIC, "批量发送消息1".getBytes()),
                new Message(MqConstant.STUDY_BATCH_TOPIC, "批量发送消息2".getBytes()),
                new Message(MqConstant.STUDY_BATCH_TOPIC, "批量发送消息3".getBytes()),
                new Message(MqConstant.STUDY_BATCH_TOPIC, "批量发送消息4".getBytes())
        );

        defaultMQProducer.send(list);

        defaultMQProducer.shutdown();
    }
}
