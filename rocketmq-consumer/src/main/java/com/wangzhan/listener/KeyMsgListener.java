package com.wangzhan.listener;

import com.wangzhan.constant.MqAnnotionConstant;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author wangzhan
 * @version 1.0
 * @description key消息监听
 * @date 2023/8/2 16:38:04
 */
@Component
@RocketMQMessageListener(topic = MqAnnotionConstant.STUDY_KEY_TOPIC, consumerGroup = MqAnnotionConstant.STUDY_KEY_GROUP, messageModel = MessageModel.CLUSTERING)
public class KeyMsgListener implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        System.out.println("消费消息：" + messageExt);
    }
}
