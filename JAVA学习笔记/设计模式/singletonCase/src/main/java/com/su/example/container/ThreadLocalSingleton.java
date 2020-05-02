package com.su.example.container;

/**
 * 线程一致的单例模式,保证每一个线程都是单例的
 */
public class ThreadLocalSingleton {
    private static final ThreadLocal<ThreadLocalSingleton> threadLocalInstanceThreadLocal
            = ThreadLocal.withInitial(() -> new ThreadLocalSingleton());
    private ThreadLocalSingleton(){

    }

    public static ThreadLocalSingleton getInstance(){
        return threadLocalInstanceThreadLocal.get();
    }
}