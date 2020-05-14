package com.su.example.annotation.demo.annotation;

import com.su.example.enums.PayTypeEnum;

import java.lang.annotation.*;

/**
 * 支付注解
 * @author xusu
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PayStrategyType {
    /**
     * 支付类型
     * @return
     */
    PayTypeEnum value();
}
