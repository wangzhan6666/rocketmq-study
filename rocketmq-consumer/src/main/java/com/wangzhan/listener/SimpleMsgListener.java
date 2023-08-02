package com.wangzhan.listener;

import com.wangzhan.constant.MqAnnotionConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * @author wangzhan
 * @version 1.0
 * @description 简单消息的监听
 * @date 2023/8/2 14:10:44
 */
/**
 * 创建一个简单消息的监听
 * 1.类上添加注解@Component和@RocketMQMessageListener
 *      @RocketMQMessageListener(topic = "powernode", consumerGroup = "powernode-group")
 *      topic指定消费的主题，consumerGroup指定消费组,一个主题可以有多个消费者组,一个消息可以被多个不同的组的消费者都消费
 * 2.实现RocketMQListener接口，注意泛型的使用，可以为具体的类型，如果想拿到消息
 * 的其他参数可以写成MessageExt
 */
@Component
// 集群模式
@RocketMQMessageListener(topic = MqAnnotionConstant.STUDY_SIMPLE_TOPIC, consumerGroup = MqAnnotionConstant.STUDY_SIMPLE_GROUP, messageModel = MessageModel.CLUSTERING)
// 广播模式
//@RocketMQMessageListener(topic = MqAnnotionConstant.STUDY_SIMPLE_TOPIC, consumerGroup = MqAnnotionConstant.STUDY_SIMPLE_GROUP, messageModel = MessageModel.BROADCASTING)
public class SimpleMsgListener implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {
    @Override
    public void onMessage(String s) {
        System.out.println("consumer1消费消息：" + s);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setInstanceName("consumer1");
    }
}
