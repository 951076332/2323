package com.msb.mall.pay.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 退款记录表
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-09
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@Document("pay_refund")
public class PayRefund extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用id
     */
    private String relAppId;

    /**
     * 上游应用的订单编号
     */
    private String relOrderNo;


    /**
     * 上游应用的退款订单编号
     */
    private String relRefundOrderNo;

    /**
     * 支付系统交易唯一编码
     */
    private String relTransactionNo;

    /**
     * 第三方交易号
     */
    private String tradeNo;

    /**
     * 支付平台生成的唯一退款单号
     */
    private String refundNo;

    /**
     * 支付渠道ID
     */
    private Integer payChannelId;

    /**
     * 退款金额
     */
    private BigDecimal refundFee;

    /**
     * 退款理由
     */
    private String refundReason;

    /**
     * 退款方式：1-自动原路返回; 2-人工打款
     */
    private Integer refundMethod;

    /**
     * 退款状态: 1-退款处理中; 2-退款成功; 3-退款失败;4-取消退款
     */
    private Integer refundStatus;

    /**
     * 商户id
     */
    private String merchantId;


}
