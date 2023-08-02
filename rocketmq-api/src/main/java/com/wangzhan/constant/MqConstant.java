package com.wangzhan.constant;

/**
 * @author wangzhan
 * @version 1.0
 * @description
 * @date 2023/7/30 20:43:27
 */
public interface MqConstant {

    public static final String NAME_SRV_ADDRESS = "127.0.0.1:9876";

    public static final String PRODUCER_GROUP = "test-producer-group";

    // 同步
    public static final String STUDY_SYNC_GROUP = "study-sync-group";
    public static final String STUDY_SYNC_TOPIC = "study-sync-topic";

    // 异步topic
    public static final String STUDY_ASYNC_GROUP = "study-async-group";
    public static final String STUDY_ASYNC_TOPIC = "study-async-topic";

    // 单向
    public static final String STUDY_ONEWAY_GROUP = "study-oneway-GROUP";
    public static final String STUDY_ONEWAY_TOPIC = "study-oneway-topic";

    // 延迟
    public static final String STUDY_DELAY_GROUP = "study-delay-GROUP";
    public static final String STUDY_DELAY_TOPIC = "study-delay-topic";

    // 顺序
    public static final String STUDY_ORDERLY_GROUP = "study-orderly-GROUP";
    public static final String STUDY_ORDERLY_TOPIC = "study-orderly-topic";

    // 批量
    public static final String STUDY_BATCH_GROUP = "study-batch-GROUP";
    public static final String STUDY_BATCH_TOPIC = "study-batch-topic";

    // 事务
    public static final String STUDY_TRANSACTION_GROUP = "study-transaction-GROUP";
    public static final String STUDY_TRANSACTION_TOPIC = "study-transaction-topic";

    // 标签
    public static final String STUDY_TAG_GROUP = "study-tag-GROUP";
    public static final String STUDY_TAG_TOPIC = "study-tag-topic";


    // key
    public static final String STUDY_KEY_GROUP = "study-key-GROUP";
    public static final String STUDY_KEY_TOPIC = "study-key-topic";

    // 重试
    public static final String STUDY_RETRY_GROUP = "study-retry-GROUP";
    public static final String STUDY_RETRY_TOPIC = "study-retry-topic";

    // 死信
    public static final String STUDY_DEAD_GROUP = "study-dead-GROUP";
    public static final String STUDY_DEAD_TOPIC = "study-dead-topic";

    // 重复
    public static final String STUDY_REPEAT_GROUP = "study-repeat-GROUP";
    public static final String STUDY_REPEAT_TOPIC = "study-repeat-topic";

}
