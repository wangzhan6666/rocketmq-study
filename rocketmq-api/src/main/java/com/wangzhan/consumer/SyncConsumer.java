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
 * @description 同步消息消费者
 * @date 2023/7/30 20:46:27
 */
public class SyncConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(MqConstant.STUDY_SYNC_GROUP);
        defaultMQPushConsumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        // 订阅主题
        defaultMQPushConsumer.subscribe(MqConstant.STUDY_SYNC_TOPIC, "*");

        // 监听同步消息
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("当前线程为：" + Thread.currentThread().getName());
                System.out.println("消息为：" + new String(list.get(0).getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 开启
        defaultMQPushConsumer.start();
        // 挂起jvm，用来测试监听
        System.in.read();

        // 关闭
        defaultMQPushConsumer.shutdown();
    }
}
