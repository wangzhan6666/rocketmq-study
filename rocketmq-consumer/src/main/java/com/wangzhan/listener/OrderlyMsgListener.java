package com.wangzhan.listener;

import com.wangzhan.constant.MqAnnotionConstant;
import com.wangzhan.domain.Order;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author wangzhan
 * @version 1.0
 * @description 顺序消息监听 : consumeMode = ConsumeMode.ORDERLY
 * @date 2023/8/2 15:29:46
 */
@Component
@RocketMQMessageListener(topic = MqAnnotionConstant.STUDY_ORDERLY_TOPIC, consumerGroup = MqAnnotionConstant.STUDY_ORDERLY_GROUP, consumeMode = ConsumeMode.ORDERLY)
public class OrderlyMsgListener implements RocketMQListener<Order> {
    @Override
    public void onMessage(Order order) {
        System.out.println("消费消息：" + order.toString());
    }
}
