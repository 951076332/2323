package com.msb.mall.pay.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * <p>
 * 支付方式
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
@Document("pay_channel")
public class PayChannel extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付方式名称
     */
    private String channelName;

    /**
     * 支付方式code
     */
    private String channelCode;

    /**
     * 支付策略类，用于策略模式实现
     */
    private String strategyBean;
    
    /**
     * 商户id
     */
    private String merchantId;


}
