package com.msb.mall.pay.dao;

import com.msb.mall.pay.entity.PayChannel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PayChannelDao extends MongoRepository<PayChannel, String> {

}
