package com.su.example.container;

/**
 * 容器式单例枚举
 * 优点：统一管理，天然免疫序列化和反射破坏单例
 * 缺点：无法动态传入参数，无法继承
 */
public enum SingletonEnum {
    INSTANCE;
}
