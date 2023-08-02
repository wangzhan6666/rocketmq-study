package com.wangzhan.listener;

import com.wangzhan.constant.MqAnnotionConstant;
import com.wangzhan.domain.Order;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author wangzhan
 * @version 1.0
 * @description 对象消息的监听
 * @date 2023/8/2 14:21:59
 */
@Component
@RocketMQMessageListener(topic = MqAnnotionConstant.STUDY_OBJ_TOPIC, consumerGroup = MqAnnotionConstant.STUDY_OBJ_GROUP, messageModel = MessageModel.CLUSTERING)
public class ObjMsgListener implements RocketMQListener<Order> {
    @Override
    public void onMessage(Order order) {
        System.out.println("消费消息：" + order.toString());
    }
}
