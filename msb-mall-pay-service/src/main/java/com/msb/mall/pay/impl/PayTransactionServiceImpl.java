package com.msb.mall.pay.impl;


import com.mongodb.client.result.UpdateResult;
import com.msb.cube.common.util.ReflectionsUtil;
import com.msb.mall.pay.base.BaseServiceImpl;
import com.msb.mall.pay.common.base.PayOrderStatusEnum;
import com.msb.mall.pay.common.util.BeanUtils;
import com.msb.mall.pay.dao.PayTransactionDao;
import com.msb.mall.pay.dto.PayTransactionDTO;
import com.msb.mall.pay.entity.PayTransaction;
import com.msb.mall.pay.interf.IPayTransactionService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 支付流水表 服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-09
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
public class PayTransactionServiceImpl extends BaseServiceImpl<PayTransactionDao, PayTransaction, String> implements IPayTransactionService {


    @Override
    public PayTransactionDTO getByOrderNo(String orderNo) {
        if(!StringUtils.isEmpty(orderNo)){

            Criteria criteria = commonBaseCriteria();
            criteria.and(ReflectionsUtil.convert(PayTransaction::getRelOrderNo)).is(orderNo);
            Query query = new Query(criteria);
            PayTransaction payTransaction = getMongoTemplate().findOne(query, entityClass);
            PayTransactionDTO payTransactionDTO = new PayTransactionDTO();
            return BeanUtils.copyBean(payTransaction, payTransactionDTO);
        }
        return null;
    }

    @Override
    public Boolean payTransactionFinished(String orderNumber, String tradeNo) {
        Criteria criteria = commonBaseCriteria();
        criteria.and(ReflectionsUtil.convert(PayTransaction::getRelOrderNo)).is(orderNumber);
        criteria.and(ReflectionsUtil.convert(PayTransaction::getTradeNo)).is(tradeNo);
        Query query = new Query(criteria);

        Update update = new Update();
        update.set(ReflectionsUtil.convert(PayTransaction::getOrderStatus), PayOrderStatusEnum.PAY_FINISHED.getValue());

        UpdateResult updateResult = getMongoTemplate().updateFirst(query, update, entityClass);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public Boolean payTransactionSuccess(@NotBlank String tradeNo, @NotNull long notifyTime,
                                         @NotNull long paymentTime, @NotBlank String orderNumber) {
        Criteria criteria = commonBaseCriteria();
        criteria.and(ReflectionsUtil.convert(PayTransaction::getRelOrderNo)).is(orderNumber);
        criteria.and(ReflectionsUtil.convert(PayTransaction::getOrderStatus)).is(PayOrderStatusEnum.PAY_PENDING.getValue());
        Query query = new Query(criteria);

        Update update = new Update();
        update.set(ReflectionsUtil.convert(PayTransaction::getTradeNo), tradeNo);
        update.set(ReflectionsUtil.convert(PayTransaction::getNotifyTime), notifyTime);
        update.set(ReflectionsUtil.convert(PayTransaction::getPaymentTime), paymentTime);
        update.set(ReflectionsUtil.convert(PayTransaction::getOrderStatus), PayOrderStatusEnum.PAY_SUCCESS.getValue());
        UpdateResult updateResult = getMongoTemplate().updateFirst(query, update, entityClass);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public Boolean refundTransactionSuccess(@NotBlank String orderNumber) {
        Criteria criteria = commonBaseCriteria();
        criteria.and(ReflectionsUtil.convert(PayTransaction::getRelOrderNo)).is(orderNumber);
        criteria.and(ReflectionsUtil.convert(PayTransaction::getOrderStatus)).is(PayOrderStatusEnum.PAY_REFUND_PENDING.getValue());
        Query query = new Query(criteria);

        Update update = new Update();
        update.set(ReflectionsUtil.convert(PayTransaction::getOrderStatus), PayOrderStatusEnum.PAY_TRADE_CLOSE.getValue());
        UpdateResult updateResult = getMongoTemplate().updateFirst(query, update, entityClass);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public Boolean refundTransactionFail(@NotBlank String orderNumber) {
        Criteria criteria = commonBaseCriteria();
        criteria.and(ReflectionsUtil.convert(PayTransaction::getRelOrderNo)).is(orderNumber);
        criteria.and(ReflectionsUtil.convert(PayTransaction::getOrderStatus)).is(PayOrderStatusEnum.PAY_REFUND_PENDING.getValue());
        Query query = new Query(criteria);

        Update update = new Update();
        update.set(ReflectionsUtil.convert(PayTransaction::getOrderStatus), PayOrderStatusEnum.PAY_SUCCESS.getValue());
        UpdateResult updateResult = getMongoTemplate().updateFirst(query, update, entityClass);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public Boolean refundTransactionPre(String orderNumber) {
        Criteria criteria = commonBaseCriteria();
        criteria.and(ReflectionsUtil.convert(PayTransaction::getRelOrderNo)).is(orderNumber);
        criteria.and(ReflectionsUtil.convert(PayTransaction::getOrderStatus)).is(PayOrderStatusEnum.PAY_SUCCESS.getValue());
        Query query = new Query(criteria);

        Update update = new Update();
        update.set(ReflectionsUtil.convert(PayTransaction::getOrderStatus), PayOrderStatusEnum.PAY_REFUND_PENDING.getValue());
        UpdateResult updateResult = getMongoTemplate().updateFirst(query, update, entityClass);
        return updateResult.getModifiedCount() > 0;
    }
}
