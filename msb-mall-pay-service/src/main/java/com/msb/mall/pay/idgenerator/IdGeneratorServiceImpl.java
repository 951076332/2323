package com.msb.mall.pay.idgenerator;

import com.msb.cube.common.base.dto.ResultWrapper;
import com.msb.cube.common.base.enums.StateCode;
import com.msb.cube.common.base.exception.BusinessException;

import com.msb.mall.pay.feign.api.IdGenerateBean;
import com.msb.mall.pay.feign.api.IdGenerateFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年08月13日 13时49分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
public class IdGeneratorServiceImpl implements IdGeneratorService {


    @Autowired
    private IdGenerateFeignClient idGenerateFeignClient;

    private final static String bizTag = "product";
    private final static String systemId = "pc";

    @Override
    public String generateIdForSnowFlake() {
        IdGenerateBean idGenerateBean = buildIdGenerateBean();
        ResultWrapper<String> result = idGenerateFeignClient.getSnowflakeId(idGenerateBean);

        if(result.getCode() != StateCode.SUCCESS.code()){
            throw new BusinessException(result.getMsg());
        }else {
            String snowFlakeId = result.getData();
            return snowFlakeId;
        }
    }

    @Override
    public String generateIdForSegment() {
        IdGenerateBean idGenerateBean = buildIdGenerateBean();
        ResultWrapper<String> result = idGenerateFeignClient.getSegmentId(idGenerateBean);
        if(result.getCode() != StateCode.SUCCESS.code()){
            throw new BusinessException(result.getMsg());
        }
        String segmentId = result.getData();
        return segmentId;
    }

    @Override
    public String generateIdForUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    /**组装生成id的参数*/
    private IdGenerateBean buildIdGenerateBean(){
        IdGenerateBean idGenerateBean = new IdGenerateBean();
        idGenerateBean.setBizTag(bizTag);
        idGenerateBean.setSystemId(systemId);
        return idGenerateBean;
    }
}
