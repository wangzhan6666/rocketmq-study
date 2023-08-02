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
 * @description 死信消息消费者
 * @date 2023/7/31 20:48:49
 */
public class DeadConsumer {
    // 模拟把消息放到 死信队列中
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(MqConstant.STUDY_DEAD_GROUP);
        defaultMQPushConsumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        defaultMQPushConsumer.subscribe(MqConstant.STUDY_DEAD_TOPIC, "*");

        // 测试最大重试次数为2次，方便测试死信队列
        defaultMQPushConsumer.setMaxReconsumeTimes(2);

        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println(new String(list.get(0).getBody()));
                System.out.println(Thread.currentThread().getName() + "----" + list);
                // 返回重试，用来测试死信队列
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });

        defaultMQPushConsumer.start();
        System.in.read();

        defaultMQPushConsumer.shutdown();
    }
}

// 消费死信队列
class DeadMqConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(MqConstant.STUDY_DEAD_GROUP);
        defaultMQPushConsumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        defaultMQPushConsumer.subscribe("%DLQ%" + MqConstant.STUDY_DEAD_GROUP, "*");

        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println(new String(list.get(0).getBody()));
                System.out.println(Thread.currentThread().getName() + "----" + list);

                // 设置消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();
        System.in.read();

        defaultMQPushConsumer.shutdown();
    }
}
