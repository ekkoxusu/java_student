package com.su.example.annotation.demo.holder;

import com.su.example.annotation.demo.IPayMoneyStrategy;
import com.su.example.enums.PayTypeEnum;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xusu
 */
@Component
public class PayMoneyStrategyContext {

    private Map<PayTypeEnum, IPayMoneyStrategy> payMoneyStrategyMap = new HashMap<>(8);

    /**
     * 对外统一暴露服务
     */
    public void strategyManage(PayTypeEnum payType, BigDecimal money) {
        IPayMoneyStrategy iPayMoneyStrategy = payMoneyStrategyMap.get(payType);
        if(iPayMoneyStrategy == null){
            throw new RuntimeException("找不到的服务");
        }
        iPayMoneyStrategy.payMoney(money);
    }

    public Map<PayTypeEnum, IPayMoneyStrategy> getPayMoneyStrategyMap() {
        return payMoneyStrategyMap;
    }
}