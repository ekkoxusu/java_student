package com.su.example;

/**
 * 懒汉式双重校验锁
 * 优点：使用时创建
 * 缺点：使用了 synchronized 不可避免的性能问题
 */
public class LazyDoubleCheckSingleton {
    // 私有化构造方法
    private LazyDoubleCheckSingleton() {}
    // 保证不会指令重排序(先新增再赋值)
    private volatile static LazyDoubleCheckSingleton INSTANCE = null;

    public static LazyDoubleCheckSingleton getInstance(){
        // 创建之后返回
        if(INSTANCE == null){
            // 如果出现并行创建则只有一个可以获得对象锁
            synchronized (LazyDoubleCheckSingleton.class){
                // 后续被关在锁外面的进来后不能再次实例化
                // 此处如果没有volatile可能指令重排序导致DCL问题，可能内部还没创建好就使用
                if(INSTANCE == null){
                    INSTANCE = new LazyDoubleCheckSingleton();
                }
            }
        }
        return INSTANCE;
    }
}
