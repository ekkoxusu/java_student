package com.su.example.mapimpl.demo.config;

import com.su.example.enums.PayTypeEnum;
import com.su.example.mapimpl.demo.IMapPayMoneyStrategy;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author su
 * 新版本order注解默认 Integer.MAX_VALUE 不会出现bean未创建问题
 */
@Component
public class MapHandlerPayMoneyStrategyProcessor {

    @Resource
    IMapPayMoneyStrategy mapAliPayStrategyImpl;
    @Resource
    IMapPayMoneyStrategy mapUnionPayStrategyImpl;
    @Resource
    IMapPayMoneyStrategy mapWeixinPayStrategyImpl;

    @Bean("payMoneyStrategyMap")
    public Map<PayTypeEnum, IMapPayMoneyStrategy> createPayMoneyStrategyMap() throws BeansException {
        Map<PayTypeEnum, IMapPayMoneyStrategy> payTypeEnumIMapPayMoneyStrategyHashMap = new HashMap(8);
        payTypeEnumIMapPayMoneyStrategyHashMap.put(PayTypeEnum.ALI_PAY,mapAliPayStrategyImpl);
        payTypeEnumIMapPayMoneyStrategyHashMap.put(PayTypeEnum.UNION_PAY,mapUnionPayStrategyImpl);
        payTypeEnumIMapPayMoneyStrategyHashMap.put(PayTypeEnum.WEIXIN_PAY,mapWeixinPayStrategyImpl);
        return payTypeEnumIMapPayMoneyStrategyHashMap;
    }
}
