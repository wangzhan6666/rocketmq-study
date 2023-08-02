package com.wangzhan.listener;

import com.wangzhan.constant.MqAnnotionConstant;
import com.wangzhan.domain.Order;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangzhan
 * @version 1.0
 * @description 对象集合消息的监听
 * @date 2023/8/2 14:31:44
 */
@Component
@RocketMQMessageListener(topic = MqAnnotionConstant.STUDY_LIST_TOPIC, consumerGroup = MqAnnotionConstant.STUDY_LIST_GROUP, messageModel = MessageModel.CLUSTERING)
public class ObjListMsgListener implements RocketMQListener<List<Order>> {

    @Override
    public void onMessage(List<Order> orders) {
        for (int i = 0; i < orders.size(); i++) {
            System.out.println("消费消息：" + orders.get(i).toString());
        }
    }
}
