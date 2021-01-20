package com.msb.mall.pay.dao;

import com.msb.mall.pay.entity.PayTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayTransactionDao extends MongoRepository<PayTransaction, String> {


}
