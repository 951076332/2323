package com.msb.mall.pay.dao;

import com.msb.mall.pay.entity.PayRefund;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRefundDao extends MongoRepository<PayRefund, String> {

}
