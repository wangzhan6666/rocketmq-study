package com.wangzhan.listener;

import com.wangzhan.constant.MqAnnotionConstant;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author wangzhan
 * @version 1.0
 * @description 事务消息的监听-消费端
 * @date 2023/8/2 16:04:19
 */
@Component
@RocketMQMessageListener(topic = MqAnnotionConstant.STUDY_TRANSACTION_TOPIC, consumerGroup = MqAnnotionConstant.STUDY_TRANSACTION_GROUP, messageModel = MessageModel.CLUSTERING)
public class TransactionMsgListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("消费消息：" + s);
    }
}
