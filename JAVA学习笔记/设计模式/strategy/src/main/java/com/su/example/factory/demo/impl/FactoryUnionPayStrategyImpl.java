package com.su.example.factory.demo.impl;


import com.su.example.factory.demo.IFactoryPayMoneyStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author xusu
 * @since 2020-04-26 10:48:23
 */
@Component
public class FactoryUnionPayStrategyImpl implements IFactoryPayMoneyStrategy {

    @Override
    public void payMoney(BigDecimal money) {
        System.out.println("我是银联支付生成金额为:"+ money +"流水开始支付");
    }
}
