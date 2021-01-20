package com.msb.mall.pay.idgenerator;

/**
 * ID 生成服务
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年08月13日 13时48分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IdGeneratorService {
    /**
     * 生成雪花算法唯一ID
     * @return uid
     */
    String generateIdForSnowFlake();

    /**
     * 生成唯一id By Segment
     * @return uid
     * */
    String generateIdForSegment();

    /**
     * 生成唯一UUID
     * @return
     */
    String generateIdForUuid();
}
