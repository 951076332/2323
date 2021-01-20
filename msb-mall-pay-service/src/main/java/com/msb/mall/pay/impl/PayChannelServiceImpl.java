package com.msb.mall.pay.impl;


import com.msb.mall.pay.base.BaseServiceImpl;
import com.msb.mall.pay.base.IBaseService;
import com.msb.mall.pay.dao.PayChannelDao;
import com.msb.mall.pay.entity.BaseEntity;
import com.msb.mall.pay.entity.PayChannel;
import com.msb.mall.pay.interf.IPayChannelService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 支付方式 服务实现类
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
public class PayChannelServiceImpl extends BaseServiceImpl<PayChannelDao, PayChannel, String> implements IPayChannelService {


}
