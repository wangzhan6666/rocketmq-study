package com.wangzhan.listener;

import com.wangzhan.constant.MqAnnotionConstant;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author wangzhan
 * @version 1.0
 * @description 异步消息监听
 * @date 2023/8/2 14:47:42
 */
@Component
@RocketMQMessageListener(topic = MqAnnotionConstant.STUDY_ASYNC_TOPIC, consumerGroup = MqAnnotionConstant.STUDY_ASYNC_GROUP, messageModel = MessageModel.CLUSTERING)
public class AsyncMsgListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("消费消息：" + s);
    }
}
