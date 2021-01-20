package com.msb.mall.pay.strategy;


import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component(value = "PayRefundStrategy")
public class PayRefundStrategy implements IStrategy{



    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {

        return null;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        return null;
    }
}
