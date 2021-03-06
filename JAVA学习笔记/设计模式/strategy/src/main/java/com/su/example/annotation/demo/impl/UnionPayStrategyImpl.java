package com.su.example.annotation.demo.impl;


import com.su.example.annotation.demo.IPayMoneyStrategy;
import com.su.example.annotation.demo.annotation.PayStrategyType;
import com.su.example.enums.PayTypeEnum;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author xusu
 * @since 2020-04-26 10:48:23
 */
@Component
@PayStrategyType(PayTypeEnum.UNION_PAY)
public class UnionPayStrategyImpl implements IPayMoneyStrategy {

    @Override
    public void payMoney(BigDecimal money) {
        System.out.println("我是银联支付生成金额为:"+ money +"流水开始支付");
    }
}
