package com.su.example.factory.demo;


import java.math.BigDecimal;

/**
 * @author xusu
 * @since 2020-04-26 10:48:23
 */
public interface IFactoryPayMoneyStrategy {
    /**
     * 付钱
     * @param money
     */
    void payMoney(BigDecimal money);
}