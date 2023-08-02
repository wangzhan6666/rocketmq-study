package com.wangzhan.producer;

import com.wangzhan.constant.MqConstant;
import com.wangzhan.domain.Order;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author wangzhan
 * @version 1.0
 * @description 顺序消息生产者
 * @date 2023/7/30 22:03:53
 */
public class OrderlyProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(MqConstant.PRODUCER_GROUP);
        defaultMQProducer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);
        defaultMQProducer.start();

        List<Order> list = Arrays.asList(
                new Order(1, 111, 59D, new Date(), "下订单"),
                new Order(2, 111, 59D, new Date(), "物流"),
                new Order(3, 111, 59D, new Date(), "签收"),
                new Order(4, 112, 89D, new Date(), "下订单"),
                new Order(5, 112, 89D, new Date(), "物流"),
                new Order(6, 112, 89D, new Date(), "拒收")
        );

        for (Order order : list) {
            Message message = new Message(MqConstant.STUDY_ORDERLY_TOPIC, order.toString().getBytes());

            try {
                defaultMQProducer.send(message, new MessageQueueSelector(){
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                        // 当前主题队列个数
                        int queueNum = list.size();

                        System.out.println(queueNum);

                        // 这个arg就是后面传入的 order.getOrderNumber()
                        Integer i = (Integer) o;
                        // 用这个值去%队列的个数得到一个队列
                        int index = i % queueNum;

                        System.out.println(o + "=======" + index);

                        // 返回选择的这个队列即可 ，那么相同的订单号 就会被放在相同的队列里 实现FIFO了
                        return list.get(index);
                    }
                }, order.getOrderNumber());
            }catch (Exception e) {
                System.out.println("发送异常");
            }
        }

        defaultMQProducer.shutdown();
    }
}
