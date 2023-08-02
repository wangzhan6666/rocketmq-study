package com.wangzhan.consumer;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author wangzhan
 * @version 1.0
 * @description 重复消费消费者
 * @date 2023/7/31 21:24:20
 */
public class RepeatConsumer {

    // 布隆过滤器
    public static BitMapBloomFilter bloomFilter = new BitMapBloomFilter(100);

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(MqConstant.STUDY_REPEAT_GROUP);
        defaultMQPushConsumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        defaultMQPushConsumer.subscribe(MqConstant.STUDY_REPEAT_TOPIC, "*");

        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                String key = list.get(0).getKeys();

                // 判断该key是否在布隆过滤器中
                if (bloomFilter.contains(key)) {    // 包含，不消费
                    System.out.println("该条数据存在布隆过滤器中 不执行业务。。。。");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                System.out.println("内容：" + new String(list.get(0).getBody()));

                // 加入到布隆过滤器中
                bloomFilter.add(key);

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();
        System.in.read();
        defaultMQPushConsumer.shutdown();
    }
}
