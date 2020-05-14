package com.su.example;

import com.su.example.annotation.demo.holder.PayMoneyStrategyContext;
import com.su.example.config.ApplicationHolder;
import com.su.example.enums.PayTypeEnum;
import com.su.example.factory.demo.holder.FactoryPayMoneyStrategyContext;
import com.su.example.mapimpl.demo.holder.MapPayMoneyStrategyContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

/**
 *
 * @author su
 */
@SpringBootApplication
public class StrategyApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrategyApplication.class, args);
		ApplicationContext applicationContext = ApplicationHolder.getApplicationContext();
		PayMoneyStrategyContext bean = applicationContext.getBean(PayMoneyStrategyContext.class);
		System.out.println("注解模式---------------------------");
		bean.strategyManage(PayTypeEnum.ALI_PAY, BigDecimal.TEN);
		bean.strategyManage(PayTypeEnum.WEIXIN_PAY, BigDecimal.TEN);
		bean.strategyManage(PayTypeEnum.UNION_PAY, BigDecimal.TEN);
		System.out.println("map模式---------------------------");
		MapPayMoneyStrategyContext mapBean = applicationContext.getBean(MapPayMoneyStrategyContext.class);
		mapBean.strategyManage(PayTypeEnum.ALI_PAY, BigDecimal.TEN);
		mapBean.strategyManage(PayTypeEnum.WEIXIN_PAY, BigDecimal.TEN);
		mapBean.strategyManage(PayTypeEnum.UNION_PAY, BigDecimal.TEN);
		System.out.println("工厂策略模式---------------------------");
		FactoryPayMoneyStrategyContext factoryBean = applicationContext.getBean(FactoryPayMoneyStrategyContext.class);
		factoryBean.strategyManage(PayTypeEnum.ALI_PAY, BigDecimal.TEN);
		factoryBean.strategyManage(PayTypeEnum.WEIXIN_PAY, BigDecimal.TEN);
		factoryBean.strategyManage(PayTypeEnum.UNION_PAY, BigDecimal.TEN);
	}

}
