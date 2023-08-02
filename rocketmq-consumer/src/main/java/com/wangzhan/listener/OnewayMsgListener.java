package com.wangzhan.listener;

import com.wangzhan.constant.MqAnnotionConstant;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author wangzhan
 * @version 1.0
 * @description 单向消息监听
 * @date 2023/8/2 14:58:16
 */
@Component
@RocketMQMessageListener(topic = MqAnnotionConstant.STUDY_ONEWAY_TOPIC, consumerGroup = MqAnnotionConstant.STUDY_ONEWAY_GROUP, messageModel = MessageModel.CLUSTERING)
public class OnewayMsgListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("消费消息：" + s);
    }
}
