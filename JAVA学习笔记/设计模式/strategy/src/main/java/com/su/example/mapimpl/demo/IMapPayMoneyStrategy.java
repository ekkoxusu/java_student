package com.su.example.mapimpl.demo;


import java.math.BigDecimal;

/**
 * @author xusu
 * @since 2020-04-26 10:48:23
 */
public interface IMapPayMoneyStrategy {
    /**
     * 付钱
     * @param money
     */
    void payMoney(BigDecimal money);
}