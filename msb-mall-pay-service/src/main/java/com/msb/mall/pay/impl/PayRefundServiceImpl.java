package com.msb.mall.pay.impl;


import com.google.common.base.Equivalence;
import com.msb.cube.common.util.ReflectionsUtil;
import com.msb.mall.pay.base.BaseServiceImpl;
import com.msb.mall.pay.base.IBaseService;
import com.msb.mall.pay.common.base.RefundStatusEnum;
import com.msb.mall.pay.common.util.BeanUtils;
import com.msb.mall.pay.dao.PayRefundDao;
import com.msb.mall.pay.dto.PayRefundDTO;
import com.msb.mall.pay.entity.PayRefund;
import com.msb.mall.pay.interf.IPayRefundService;
import com.msb.mall.pay.interf.IPayTransactionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 退款记录表 服务实现类
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
public class PayRefundServiceImpl extends BaseServiceImpl<PayRefundDao, PayRefund, String> implements IPayRefundService {

    @Autowired
    private IPayTransactionService payTransactionService;
    @Autowired
    private PayRefundDao payRefundDao;

    @Override
    public PayRefundDTO getByRefundOrderNo(String orderNo) throws Exception {
        if (StringUtils.isEmpty(orderNo)){
            return null;
        }
        Criteria criteria = commonBaseCriteria();
        criteria.and(ReflectionsUtil.convert(PayRefund::getRelRefundOrderNo)).is(orderNo);
        Query query = new Query(criteria);
        PayRefund payRefund = getMongoTemplate().findOne(query, entityClass);
        return BeanUtils.copyBean(payRefund, PayRefundDTO.class);
    }

    @Override
    @Transactional
    public Boolean refundSuccess(String refundOrderNo, String orderNo) {
        Criteria criteria = commonBaseCriteria();
        criteria.and(ReflectionsUtil.convert(PayRefund::getRelRefundOrderNo)).is(refundOrderNo);
        criteria.and(ReflectionsUtil.convert(PayRefund::getRefundStatus)).is(RefundStatusEnum.REFUND_PENDING.getValue());

        Query query = new Query(criteria);
        Update update = new Update();
        update.set(ReflectionsUtil.convert(PayRefund::getRefundStatus), RefundStatusEnum.REFUND_SUCCESS.getValue());
        getMongoTemplate().updateFirst(query, update, entityClass);
        return payTransactionService.refundTransactionSuccess(orderNo);
    }


    @Override
    public Boolean refundFail(String refundOrderNo,String orderNo) {
        Criteria criteria = commonBaseCriteria();
        criteria.and(ReflectionsUtil.convert(PayRefund::getRelRefundOrderNo)).is(refundOrderNo);
        criteria.and(ReflectionsUtil.convert(PayRefund::getRefundStatus)).is(RefundStatusEnum.REFUND_PENDING.getValue());

        Query query = new Query(criteria);
        Update update = new Update();
        update.set(ReflectionsUtil.convert(PayRefund::getRefundStatus), RefundStatusEnum.REFUND_FAIL.getValue());
        getMongoTemplate().updateFirst(query, update, entityClass);

        return  payTransactionService.refundTransactionFail(orderNo);
    }
}
