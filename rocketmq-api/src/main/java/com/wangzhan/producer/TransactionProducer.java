package com.wangzhan.producer;

import com.wangzhan.constant.MqConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Date;

/**
 * @author wangzhan
 * @version 1.0
 * @description 事务消息生产者
 * @date 2023/7/31 09:08:05
 */
public class TransactionProducer {
    public static void main(String[] args) throws Exception {
        TransactionMQProducer transactionMQProducer = new TransactionMQProducer(MqConstant.PRODUCER_GROUP);
        transactionMQProducer.setNamesrvAddr(MqConstant.NAME_SRV_ADDRESS);

        // 设置事务消息监听器
        transactionMQProducer.setTransactionListener(new TransactionListener() {
            // 这个是执行本地业务方法
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {

                System.out.println("业务方法@@@@@@@@" + new Date());
                System.out.println("业务方法@@@@@@@@" + new String(message.getBody()));

                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // COMMIT_MESSAGE 表示允许消费者消费该消息
                // ROLLBACK_MESSAGE 表示该消息将被删除，不允许消费
                // UNKNOW表示需要MQ回查才能确定状态 那么过一会 代码会走下面的checkLocalTransaction(msg)方法
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            // 回查方法：回查不是再次执行业务操作，而是确认上面的操作是否有结果
            // 默认是1min回查 默认回查15次 超过次数则丢弃打印日志 可以通过参数设置
            // transactionTimeOut 超时时间
            // transactionCheckMax 最大回查次数
            // transactionCheckInterval 回查间隔时间单位毫秒
            // 触发条件
            // 1.当上面执行本地事务返回结果UNKNOW时,或者下面的回查方法也返回UNKNOW时 会触发回查
            // 2.当上面操作超过20s没有做出一个结果，也就是超时或者卡住了，也会进行回查
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("回查方法========" + new Date());
                System.out.println("回查方法========" + messageExt.toString());
                return LocalTransactionState.UNKNOW;
            }
        });


        transactionMQProducer.start();
        Message message = new Message(MqConstant.STUDY_TRANSACTION_TOPIC, "发送事务消息".getBytes());
        transactionMQProducer.sendMessageInTransaction(message, null);

        // 为了测试事务监听方法
        System.in.read();
        transactionMQProducer.shutdown();
    }
}
