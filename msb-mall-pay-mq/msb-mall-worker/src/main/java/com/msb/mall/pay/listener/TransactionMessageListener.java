package com.msb.mall.pay.listener;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 全局事务消息 监听
 */
@Slf4j
@Service
@RocketMQTransactionListener
public class TransactionMessageListener implements RocketMQLocalTransactionListener {

    @Resource
    private TransactionMessageResolve transactionMessageResolve;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        return transactionMessageResolve.executeLocalTransaction(msg, arg);
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {

        return transactionMessageResolve.checkLocalTransaction(msg);
    }

}
