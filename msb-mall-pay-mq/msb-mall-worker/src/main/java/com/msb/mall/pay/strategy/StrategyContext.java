package com.msb.mall.pay.strategy;



import com.msb.mall.pay.SpringContextUtil;
import com.msb.trading.mq.contant.MQTopic;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.messaging.Message;

/**
 * 抽象策略类
 */
public class StrategyContext {

    private IStrategy iStrategy;

    private Object arg;

    private Message msg;

    public StrategyContext(Message msg){
        this.msg = msg;
    }

    public StrategyContext(Message msg, Object arg){
        this.msg = msg;
        this.arg = arg;
        this.iStrategy = chooseStrategy(msg);
    }

    public RocketMQLocalTransactionState executeLocalTransaction(){
       return this.iStrategy.executeLocalTransaction(this.msg, this.arg);
    }

    public RocketMQLocalTransactionState checkLocalTransaction(){
        return this.iStrategy.checkLocalTransaction(this.msg);
    }

    private IStrategy chooseStrategy(Message msg){
        String topic = getTopic(msg);
        IStrategy strategy = null;
        switch (topic){
            case MQTopic.NOTIFY_PAY_REFUND_ACCEPT:
                strategy = SpringContextUtil.getBean(PayRefundStrategy.class);
                break;
        }
        return strategy;
    }

    private String getTransactionId(Message message){
        return (String) getHeader(message, RocketMQHeaders.TRANSACTION_ID);
    }

    private String getTopic(Message message){
        return  (String) getHeader(message, RocketMQHeaders.TOPIC);
    }

    private Object getHeader(Message message, String key) {
        return message.getHeaders().get(RocketMQUtil.toRocketHeaderKey(key));
    }
}
