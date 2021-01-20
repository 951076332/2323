package com.msb.mall.pay.strategy;

import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

public interface IStrategy {

    RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg);

    RocketMQLocalTransactionState checkLocalTransaction(Message msg);

}
