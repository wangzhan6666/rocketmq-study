package com.wangzhan.producer;

import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.UUID;

/**
 * @author wangzhan
 * @version 1.0
 * @description 重复消费生产者
 * @date 2023/7/31 21:23:53
 */
public class RepeatProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(MqConstant.PRODUCER_GROUP);
        defaultMQProducer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        defaultMQProducer.start();

        // 测试 使用固定key，方便测试布隆过滤器
        String keyId = "baa933ed-c652-4f36-a2a8-0bd4054cb2fd";

        Message message = new Message(MqConstant.STUDY_REPEAT_TOPIC, "tagA", keyId, ("测试重复消息" + keyId).getBytes());
        SendResult send = defaultMQProducer.send(message);

        System.out.println(send);
        defaultMQProducer.shutdown();

    }
}
