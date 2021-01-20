package com.msb.mall.pay.sender.impl;


import com.msb.mall.pay.dto.RefundOrderDTO;
import com.msb.mall.pay.sender.IPayMessageSender;
import com.msb.trading.dto.order.OmsOrderDTO;
import com.msb.trading.mq.contant.MQTopic;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class PayMessageSenderImpl implements IPayMessageSender {

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    @Override
    public void payRefundSend(RefundOrderDTO refundOrderDTO) {
        rocketMQTemplate.syncSend(MQTopic.NOTIFY_PAY_REFUND_ACCEPT, refundOrderDTO);
    }

    @Override
    public void paySuccessSend(OmsOrderDTO orderDTO) {
        rocketMQTemplate.syncSend(MQTopic.NOTIFY_PAY_SUCCESS_ACCEPT, orderDTO);
    }
}
