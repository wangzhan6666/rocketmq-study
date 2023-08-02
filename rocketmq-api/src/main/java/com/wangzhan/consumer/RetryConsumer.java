package com.wangzhan.consumer;

import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author wangzhan
 * @version 1.0
 * @description 重试消息消费者
 * @date 2023/7/31 17:29:41
 */
public class RetryConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(MqConstant.STUDY_RETRY_GROUP);
        defaultMQPushConsumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        defaultMQPushConsumer.subscribe(MqConstant.STUDY_RETRY_TOPIC, "*");

        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                try {
                    System.out.println(new String(list.get(0).getBody()));
                    // TODO 制造异常
                    int i = 10 / 0;
                }catch (Exception e) {
                    // 出现问题  判断重试次数
                    MessageExt messageExt = list.get(0);

                    // 获取重试次数 失败一次消息中的失败次数会累加一次
                    int reconsumeTimes = messageExt.getReconsumeTimes();

                    System.out.println("重试次数：" + reconsumeTimes);

                    if (reconsumeTimes >= 3) {
                        // TODO 重试3次失败后，则把消息记录到日志或者数据库中，通过人工来处理
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }else {
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();

        System.in.read();
        defaultMQPushConsumer.shutdown();
    }
}
