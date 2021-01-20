package com.msb.mall.pay.strategy;


import com.msb.mall.pay.interf.IPayStrategyService;
import com.msb.uc.auth.UserContextHolder;
import com.msb.uc.model.LoginUserInfo;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 抽象支付策略
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月08日 10时23分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public abstract class AbstractPayStrategyService implements IPayStrategyService {

    /**
     * 处理支付宝回调通知
     * @param request
     * @return
     */
    public Map<String,String> parseAliPayCallback(HttpServletRequest request,boolean notify){
        Map<String,String> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            if(notify){
                params.put(name,valueStr);
            }else{
                // 乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("utf-8"));
                params.put(name, valueStr);
            }
        }
        return params;
    }


    /**
     * 从上下文获取用户信息,有可能为null
     *
     * @return
     */
    protected LoginUserInfo getCurrentUser() {
        return UserContextHolder.getCurrentUser().orElse(null);
    }
}
