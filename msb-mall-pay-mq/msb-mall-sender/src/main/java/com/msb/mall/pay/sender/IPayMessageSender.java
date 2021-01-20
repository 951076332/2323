package com.msb.mall.pay.sender;


import com.msb.mall.pay.dto.RefundOrderDTO;
import com.msb.trading.dto.order.OmsOrderDTO;

/**
 * 订单 消息发送服务
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact lixiaoshome@163.com
 * @date: 2020-11-18 16:31
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 **/
public interface IPayMessageSender {

    void payRefundSend(RefundOrderDTO refundOrderDTO);

    void paySuccessSend(OmsOrderDTO orderDTO);

}
