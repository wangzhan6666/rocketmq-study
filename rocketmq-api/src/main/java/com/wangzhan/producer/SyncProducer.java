package com.wangzhan.producer;

import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author wangzhan
 * @version 1.0
 * @description 同步消息生产者
 * @date 2023/7/30 20:47:06
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        // 创建生产者
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(MqConstant.PRODUCER_GROUP);
        // 连接namesrv
        defaultMQProducer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        // 开启
        defaultMQProducer.start();

        // 准备消息
        Message message = new Message(MqConstant.STUDY_SYNC_TOPIC, "学习发送同步消息".getBytes());
        defaultMQProducer.send(message);    // 发送同步消息

        // 关闭
        defaultMQProducer.shutdown();
    }
}
