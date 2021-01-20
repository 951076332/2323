package com.msb.mall.pay.listener;


import com.msb.mall.pay.message.contant.MQGroup;
import com.msb.mall.pay.strategy.PayContextStrategy;
import com.msb.trading.mq.contant.MQTopic;
import com.msb.trading.mq.message.RefundMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 退款消息监听
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = MQGroup.GROUP_NOTIFY_PAY_CENTER_REFUND, topic = MQTopic.NOTIFY_PAY_REFUND_ACCEPT)
public class OrderRefundMessageListener implements RocketMQListener<RefundMsg> {

    @Autowired
    private PayContextStrategy payContextStrategy;

    @Override
    public void onMessage(RefundMsg message) {
        try {
            payContextStrategy.refund(message);
        } catch (Exception e) {
            throw new RuntimeException("退款错误");
        }
    }
}
