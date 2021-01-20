package com.msb.mall.pay.feign.api;


import com.msb.cube.common.base.dto.ResultWrapper;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/9/21/16:56
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@FeignClient(value = "ID-GENERATOR", path = "generator/id")
public interface IdGenerateFeignClient {

    /**
     * segmentId
     * @param idGenerateBean
     * @return ResultWrapper
     */
    @PostMapping("/api/segment")
    ResultWrapper<String> getSegmentId(@RequestBody IdGenerateBean idGenerateBean);

    /**
     * snowflakeId
     * @param idGenerateBean
     * @return ResultWrapper
     */
    @PostMapping("/api/snowflake")
    ResultWrapper<String> getSnowflakeId(@RequestBody IdGenerateBean idGenerateBean);

}




