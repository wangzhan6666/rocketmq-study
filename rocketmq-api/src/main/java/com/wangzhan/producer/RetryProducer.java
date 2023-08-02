package com.wangzhan.producer;

import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author wangzhan
 * @version 1.0
 * @description 重试消息生产者
 * @date 2023/7/31 17:29:09
 */
public class RetryProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(MqConstant.PRODUCER_GROUP);
        defaultMQProducer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);

        defaultMQProducer.start();

        Message message = new Message(MqConstant.STUDY_RETRY_TOPIC, "发送重试消息".getBytes());
        defaultMQProducer.send(message);
        defaultMQProducer.setRetryTimesWhenSendFailed(3);   // 发送消息失败后重试3次
        defaultMQProducer.send(message, 1000);  // 发送超过1s后进行重试

        defaultMQProducer.shutdown();

    }
}
