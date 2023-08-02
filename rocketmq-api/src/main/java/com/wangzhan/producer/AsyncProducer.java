package com.wangzhan.producer;

import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author wangzhan
 * @version 1.0
 * @description 异步消息生产者
 * @date 2023/7/30 21:03:21
 */
public class AsyncProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(MqConstant.PRODUCER_GROUP);
        defaultMQProducer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);

        defaultMQProducer.start();

        Message message = new Message(MqConstant.STUDY_ASYNC_TOPIC, "学习发送异步消息".getBytes());
        defaultMQProducer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送异步消息成功");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送异步消息失败");
            }
        });

        // 挂起jvm，测试异步发送消息
        System.in.read();
        // 关闭
        defaultMQProducer.shutdown();
    }
}
