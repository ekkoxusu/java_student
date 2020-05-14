package com.su.example.factory.demo.holder;

import com.su.example.enums.PayTypeEnum;
import com.su.example.factory.demo.IFactoryPayMoneyStrategy;
import com.su.example.factory.demo.impl.FactoryAliPayStrategyImpl;
import com.su.example.factory.demo.impl.FactoryUnionPayStrategyImpl;
import com.su.example.factory.demo.impl.FactoryWeixinPayStrategyImpl;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author xusu
 */
@Component
public class FactoryPayMoneyStrategyContext {

    /**
     * 对外统一暴露服务
     */
    public void strategyManage(PayTypeEnum payType, BigDecimal money) {
        IFactoryPayMoneyStrategy iFactoryPayMoneyStrategy;
        switch (payType){
            case ALI_PAY:
                iFactoryPayMoneyStrategy =  new FactoryAliPayStrategyImpl();
                break;
            case WEIXIN_PAY:
                iFactoryPayMoneyStrategy = new FactoryWeixinPayStrategyImpl();
                break;
            case UNION_PAY:
                iFactoryPayMoneyStrategy = new FactoryUnionPayStrategyImpl();
                break;
            default: throw new RuntimeException("找不到的服务");
        }
        iFactoryPayMoneyStrategy.payMoney(money);
    }

}