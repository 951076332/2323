package com.msb.mall.pay.feign.api;


import com.msb.cube.common.base.dto.ResultWrapper;
import com.msb.trading.dto.order.OmsOrderAndItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/9/11/14:35
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@FeignClient(name = "msb-trading-center")
public interface IOmsOrderFeignService {

    @GetMapping("/trading-center/oms/oms-order/v1/detail")
    public ResultWrapper<OmsOrderAndItemDTO> orderDetail(@NotBlank(message = "订单编号不能为空") @RequestParam("orderNo") String orderNo);
}
