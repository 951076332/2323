package com.msb.mall.pay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(value="RefundOrderDTO传输实体", description="订单退款dto")
public class RefundOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "退款单编号")
    private String refundOrderNo;

    @ApiModelProperty(value = "退款状态: 1-退款处理中; 2-退款成功; 3-退款失败;4-取消退款")
    private Integer refundStatus;
}
