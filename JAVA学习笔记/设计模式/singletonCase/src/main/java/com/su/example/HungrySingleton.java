package com.su.example;

/**
 * 饿汉式单例模式
 * 优点：保证线程安全
 * 缺点：还未使用就创建浪费内存空间
 */
public class HungrySingleton {
    // 私有化构造方法
    private HungrySingleton(){}
    // 在类加载的同时创建该元素
    private final static HungrySingleton INSTANCE = new HungrySingleton();

    public static HungrySingleton getInstance(){
        return INSTANCE;
    }
}
