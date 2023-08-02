package com.wangzhan.listener;

import com.wangzhan.constant.MqAnnotionConstant;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author wangzhan
 * @version 1.0
 * @description 标签tag消息监听
 * @date 2023/8/2 16:21:49
 */
@Component
@RocketMQMessageListener(topic = MqAnnotionConstant.STUDY_TAG_TOPIC,
        consumerGroup = MqAnnotionConstant.STUDY_TAG_GROUP,
        messageModel = MessageModel.CLUSTERING,
        selectorType = SelectorType.TAG,
        selectorExpression = "tagA || tagC")
public class TagMsgListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("消费消息：" + s);
    }
}
