package com.wangzhan.controller;

import com.wangzhan.constant.MqAnnotionConstant;
import com.wangzhan.domain.Order;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author wangzhan
 * @version 1.0
 * @description mq生产者controller层
 * @date 2023/8/2 12:20:11
 */
@RestController
@RequestMapping("/mq/producer")
public class MqProducerController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    // http://localhost:8085/mq/producer/sendSimpleMsg
    // 发送简单消息
    @GetMapping(value = "/sendSimpleMsg")
    public Object sendSimpleMsg() {

        for (int i = 0; i < 10; i++) {
            rocketMQTemplate.syncSend(MqAnnotionConstant.STUDY_SIMPLE_TOPIC, "这是一个简单消息"+i);
        }

        return "发送结束";
    }

    // http://localhost:8085/mq/producer/sendObjMsg
    // 发送对象消息
    @GetMapping(value = "/sendObjMsg")
    public Object sendObjMsg() {
        Order order = new Order(UUID.randomUUID().toString(), 111, 998D, new Date(), "加急配送");

        SendResult sendResult = rocketMQTemplate.syncSend(MqAnnotionConstant.STUDY_OBJ_TOPIC, order);
        return sendResult;
    }

    // http://localhost:8085/mq/producer/sendObjListMsg
    // 发送对象集合消息
    @GetMapping(value = "sendObjListMsg")
    public Object sendObjListMsg() {
        List<Order> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Order order = new Order(UUID.randomUUID().toString(), i, 998D, new Date(), "加急配送" + i);
            list.add(order);
        }

        SendResult sendResult = rocketMQTemplate.syncSend(MqAnnotionConstant.STUDY_LIST_TOPIC, list);

        return sendResult;
    }

    // http://localhost:8085/mq/producer/sendAsyncMsg
    // 异步发送消息
    @GetMapping(value = "sendAsyncMsg")
    public Object sendAsyncMsg() {
        rocketMQTemplate.asyncSend(MqAnnotionConstant.STUDY_ASYNC_TOPIC, "这是一个异步消息", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送消息成功：" + sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送消息失败ERROR：" + throwable.toString());
            }
        });

        return "发送结束";
    }

    // http://localhost:8085/mq/producer/sendOnewayMsg
    // 发送单向消息
    @GetMapping(value = "sendOnewayMsg")
    public Object sendOnewayMsg() {
        rocketMQTemplate.sendOneWay(MqAnnotionConstant.STUDY_ONEWAY_TOPIC, "这是一个单向消息");

        return "发送结束";
    }

    // http://localhost:8085/mq/producer/sendDelayMsg
    // 发送延迟消息
    @GetMapping(value = "sendDelayMsg")
    public Object sendDelayMsg() {

        // 构建消息对象
        Message<String> message = MessageBuilder.withPayload(new Date() + "这是一个延迟消息").build();

        // syncSend(String destination - topic, Message<?> message - 消息, long timeout - 发送消息的超时时间, int delayLevel - 延迟等级)
        SendResult sendResult = rocketMQTemplate.syncSend(MqAnnotionConstant.STUDY_DELAY_TOPIC, message, 2000, 3);

        return sendResult;
    }

    // http://localhost:8085/mq/producer/sendOrderlyMsg
    // 发送顺序消息
    @GetMapping(value = "sendOrderlyMsg")
    public Object sendOrderlyMsg() {
        // 初始化数据
        List<Order> orders = Arrays.asList(
                new Order(UUID.randomUUID().toString(), 111, Math.random() * 1000, new Date(), "张三的下订单"),
                new Order(UUID.randomUUID().toString(), 111, Math.random() * 1000, new Date(), "张三的发短信"),
                new Order(UUID.randomUUID().toString(), 111, Math.random() * 1000, new Date(), "张三的物流"),
                new Order(UUID.randomUUID().toString(), 111, Math.random() * 1000, new Date(), "张三的签收"),

                new Order(UUID.randomUUID().toString(), 222, Math.random() * 1000, new Date(), "李四的下订单"),
                new Order(UUID.randomUUID().toString(), 222, Math.random() * 1000, new Date(), "李四的发短信"),
                new Order(UUID.randomUUID().toString(), 222, Math.random() * 1000, new Date(), "李四的物流"),
                new Order(UUID.randomUUID().toString(), 222, Math.random() * 1000, new Date(), "李四的签收")
        );

        for (Order order : orders) {
            // 我们控制流程为 下订单->发短信->物流->签收    根据订单编号 发送到同一个队列中
            rocketMQTemplate.syncSendOrderly(MqAnnotionConstant.STUDY_ORDERLY_TOPIC, order, String.valueOf(order.getOrderNumber()));
        }

        return "发送结束";
    }

    // http://localhost:8085/mq/producer/sendTransactionMsg
    // 发送事务消息
    @RequestMapping(value = "sendTransactionMsg")
    public Object sendTransactionMsg() {

        Message<String> message = MessageBuilder.withPayload("这是一个事务消息。。。").build();
        // sendMessageInTransaction(String destination - topic, Message<?> message - 构建消息, Object arg)
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(MqAnnotionConstant.STUDY_TRANSACTION_TOPIC, message, null);

        return transactionSendResult;
    }

    // http://localhost:8085/mq/producer/sendTagMsg
    // 发送标签tag消息
    @GetMapping(value = "sendTagMsg")
    public Object sendTagMsg() {
        rocketMQTemplate.syncSend(MqAnnotionConstant.STUDY_TAG_TOPIC + ":tagA", "这是一个标签tagA消息");
        rocketMQTemplate.syncSend(MqAnnotionConstant.STUDY_TAG_TOPIC + ":tagB", "这是一个标签tagB消息");
        rocketMQTemplate.syncSend(MqAnnotionConstant.STUDY_TAG_TOPIC + ":tagC", "这是一个标签tagC消息");

        return "发送结束";
    }

    // http://localhost:8085/mq/producer/sendKeyMsg
    // 发送key消息
    // Key 一般用于消息在业务层面的唯一标识。对发送的消息设置好 Key，以后可以根据这个 Key 来查找消息。比如消息异常，消息丢失，进行查找会很 方便。RocketMQ 会创建专门的索引文件，用来存储 Key 与消息的映射，由于是 Hash 索引，应尽量使 Key 唯一，避免潜在的哈希冲突。
    @GetMapping(value = "sendKeyMsg")
    public Object sendKeyMsg() {

        Message<String> message = MessageBuilder.withPayload("这是一个key消息").setHeader("KEYS", "测试key").build();

        SendResult sendResult = rocketMQTemplate.syncSend(MqAnnotionConstant.STUDY_KEY_TOPIC, message);

        return sendResult;
    }

}
