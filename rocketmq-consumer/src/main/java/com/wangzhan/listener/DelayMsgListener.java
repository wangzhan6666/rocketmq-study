package com.wangzhan.listener;

import com.wangzhan.constant.MqAnnotionConstant;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author wangzhan
 * @version 1.0
 * @description 延迟消息监听
 * @date 2023/8/2 15:06:36
 */
@Component
@RocketMQMessageListener(topic = MqAnnotionConstant.STUDY_DELAY_TOPIC, consumerGroup = MqAnnotionConstant.STUDY_DELAY_GROUP, messageModel = MessageModel.CLUSTERING)
public class DelayMsgListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("消费消息：开始时间-" + s + ", 消费时间-" + new Date());
    }
}
