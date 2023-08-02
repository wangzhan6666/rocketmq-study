package com.wangzhan.consumer;

import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author wangzhan
 * @version 1.0
 * @description key消息消费者
 * @date 2023/7/31 16:09:11
 */
public class KeyConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(MqConstant.STUDY_KEY_GROUP);
        defaultMQPushConsumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        defaultMQPushConsumer.subscribe(MqConstant.STUDY_KEY_TOPIC, "tagA");

        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("body:" + new String(list.get(0).getBody()));
                System.out.println("messageID:" + list.get(0).getMsgId());
                System.out.println("tag:" + list.get(0).getTags());
                System.out.println("key:" + list.get(0).getKeys());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();

        System.in.read();
        defaultMQPushConsumer.shutdown();
    }
}
