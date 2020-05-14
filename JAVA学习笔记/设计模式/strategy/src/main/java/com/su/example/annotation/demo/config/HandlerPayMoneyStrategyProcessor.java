package com.su.example.annotation.demo.config;

import com.su.example.annotation.demo.IPayMoneyStrategy;
import com.su.example.annotation.demo.annotation.PayStrategyType;
import com.su.example.annotation.demo.holder.PayMoneyStrategyContext;
import com.su.example.enums.PayTypeEnum;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author su
 * 新版本order注解默认 Integer.MAX_VALUE 不会出现bean未创建问题
 */
@Component
@Order
public class HandlerPayMoneyStrategyProcessor implements ApplicationContextAware {

    /**
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取所有策略注解的Bean
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(PayStrategyType.class);
        PayMoneyStrategyContext bean = applicationContext.getBean(PayMoneyStrategyContext.class);
        beanMap.forEach((k,v)->{
            PayTypeEnum type = AopUtils.getTargetClass(v).getAnnotation(PayStrategyType.class).value();
            //将class加入map中,type作为key
            bean.getPayMoneyStrategyMap().put(type,(IPayMoneyStrategy) v);
        });
    }
}
