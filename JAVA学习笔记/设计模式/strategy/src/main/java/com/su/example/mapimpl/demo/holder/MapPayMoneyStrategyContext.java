package com.su.example.mapimpl.demo.holder;

import com.su.example.enums.PayTypeEnum;
import com.su.example.mapimpl.demo.IMapPayMoneyStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author xusu
 */
@Component
public class MapPayMoneyStrategyContext {

    @Resource
    private Map<PayTypeEnum, IMapPayMoneyStrategy> payMoneyStrategyMap;
    /**
     * 对外统一暴露服务
     */
    public void strategyManage(PayTypeEnum payType, BigDecimal money) {
        IMapPayMoneyStrategy iPayMoneyStrategy = payMoneyStrategyMap.get(payType);
        if(iPayMoneyStrategy == null){
            throw new RuntimeException("找不到的服务");
        }
        iPayMoneyStrategy.payMoney(money);
    }

}