package com.wangzhan.producer;

import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.Date;

/**
 * @author wangzhan
 * @version 1.0
 * @description 延迟消息生产者
 * @date 2023/7/30 21:52:21
 */
public class DelayProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(MqConstant.PRODUCER_GROUP);
        defaultMQProducer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        defaultMQProducer.start();

        Message message = new Message(MqConstant.STUDY_DELAY_TOPIC, "发送延迟消息".getBytes());
        // 设置消息的延迟等级："1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h"
        message.setDelayTimeLevel(3);

        defaultMQProducer.send(message);

        // 当前时间
        System.out.println(new Date());
        // 关闭实例
        defaultMQProducer.shutdown();
    }
}
