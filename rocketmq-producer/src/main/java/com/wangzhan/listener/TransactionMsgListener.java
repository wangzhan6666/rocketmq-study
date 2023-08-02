package com.wangzhan.listener;


import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author wangzhan
 * @version 1.0
 * @description 发送事务消息的监听:
 *                  1.消息会先到事务监听类的执行方法，
 *                  2.如果返回状态为COMMIT，则消费者可以直接监听到
 *                  3.如果返回状态为ROLLBACK，则消息发送失败，直接回滚
 *                  4.如果返回状态为UNKNOW，则过一会会走回查方法
 *                  5.如果回查方法返回状态为UNKNOW或者ROLLBACK，则消息发送失败，直接回滚
 *                  6.如果回查方法返回状态为COMMIT，则消费者可以直接监听到
 *
 * @date 2023/8/2 15:52:24
 */
@Component
@RocketMQTransactionListener(corePoolSize = 4, maximumPoolSize = 8)
public class TransactionMsgListener implements RocketMQLocalTransactionListener {

    /**
     * 执行本地事务，这里可以执行一些业务
     * 比如操作数据库，操作成功就return RocketMQLocalTransactionState.COMMIT;
     * 可以使用try catch来控制成功或者失败;
     *
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        // 拿到消息参数
        System.out.println(o);
        // 拿到消息头
        System.out.println(message.getHeaders());
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    /**
     * 回查本地事务，只有上面的执行方法返回UNKNOWN时，才执行下面的方法 默认是1min回查
     * 此方法为回查方法，执行需要等待一会
     * xxx.isSuccess()  这里可以执行一些检查的方法
     * 如果返回COMMIT，那么本地事务就算是提交成功了，消息就会被消费者看到
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(org.springframework.messaging.Message message) {
        System.out.println("====进入回查方法了===");
        // 可以执行一些业务，例如：查询数据库中是否有该条数据，若有则return RocketMQLocalTransactionState.COMMIT，否则回滚 return RocketMQLocalTransactionState.ROLLBACK
        return RocketMQLocalTransactionState.COMMIT;
    }
}
