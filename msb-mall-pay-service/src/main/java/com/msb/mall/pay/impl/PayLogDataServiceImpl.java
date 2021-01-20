package com.msb.mall.pay.impl;


import com.msb.mall.pay.dao.PayLogDataRepository;
import com.msb.mall.pay.entity.PayLogData;
import com.msb.mall.pay.interf.IPayLogDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易日志业务实现类
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月17日 17时34分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
@Slf4j
public class PayLogDataServiceImpl implements IPayLogDataService {
    @Autowired
    private PayLogDataRepository payLogDataRepository;

    @Override
    public PayLogData saveLogData(PayLogData payLogData) {
        return  payLogDataRepository.save(payLogData);
    }
}
