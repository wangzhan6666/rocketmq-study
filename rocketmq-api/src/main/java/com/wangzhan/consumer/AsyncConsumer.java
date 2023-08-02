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
 * @description 异步消息消费者
 * @date 2023/7/30 21:03:59
 */
public class AsyncConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(MqConstant.STUDY_ASYNC_GROUP);
        defaultMQPushConsumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        defaultMQPushConsumer.subscribe(MqConstant.STUDY_ASYNC_TOPIC, "*");

        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                // 这里执行消费的代码 默认是多线程消费
                System.out.println(new String(list.get(0).getBody()));
                System.out.println(Thread.currentThread().getName() + "----" + list);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();

        System.in.read();
        defaultMQPushConsumer.shutdown();

    }
}
