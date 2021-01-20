package com.msb.mall.pay.listener;


import com.msb.mall.pay.strategy.StrategyContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionMessageResolve {


    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        StrategyContext strategyContext = new StrategyContext(message, arg);
        return strategyContext.executeLocalTransaction();
    }

    /**
     * 处理事务会查
     *
     * @param message
     * @return
     */
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        StrategyContext strategyContext = new StrategyContext(message);
        return strategyContext.checkLocalTransaction();
    }



}
