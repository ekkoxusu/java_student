package com.su.example;

import java.io.Serializable;

/**
 * 懒汉式静态内部类
 * 优点：使用时创建
 * 缺点：因为使用静态内部类创建，无可避免的无法将参数传入
 * 静态内部类为什么是线程安全的且在使用的时候创建
 * 类加载时机：JAVA虚拟机在有且仅有的5种场景下会对类进行初始化。
 * 1.遇到new、getstatic、setstatic或者invokestatic这4个字节码指令时，对应的java代码场景为：new一个关键字或者一个实例化对象时、读取或设置一个静态字段时
 * (final修饰、已在编译期把结果放入常量池的除外)、调用一个类的静态方法时。
 * 2.使用java.lang.reflect包的方法对类进行反射调用的时候，如果类没进行初始化，需要先调用其初始化方法进行初始化。
 * 3.当初始化一个类时，如果其父类还未进行初始化，会先触发其父类的初始化。
 * 4.当虚拟机启动时，用户需要指定一个要执行的主类(包含main()方法的类)，虚拟机会先初始化这个类。
 * 5.当使用JDK 1.7等动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后的解析结果REF_getStatic、REF_putStatic、REF_invokeStatic
 * 的方法句柄，并且这个方法句柄所对应的类没有进行过初始化，则需要先触发其初始化。
 * 这5种情况被称为是类的主动引用，注意，这里《虚拟机规范》中使用的限定词是"有且仅有"，那么，除此之外的所有引用类都不会对类进行初始化，称为被动引用。静态内部类就属于被动引用的行列。
 * 虚拟机会保证一个类的<clinit>()方法在多线程环境中被正确地加锁、同步，如果多个线程同时去初始化一个类，那么只会有一个线程去执行这个类的<clinit>()方法，其他线程都需要阻塞等待，直到活动线程执行<clinit>()方法完毕
 */
public class LazyStaticClassSingleton implements Serializable {

    private static final long serialVersionUID = 7253412778069852563L;

    // 私有化构造方法，解决反射创建
    private LazyStaticClassSingleton() {
        if(LazyStaticClassSingletonHolder.INSTANCE != null){
            throw new RuntimeException("不允许再次创建");
        }
    }
    // 保证不会指令重排序(先新增再赋值)

    public static LazyStaticClassSingleton getInstance(){

        return LazyStaticClassSingletonHolder.INSTANCE;
    }
    private static class LazyStaticClassSingletonHolder{
        private final static LazyStaticClassSingleton INSTANCE = new LazyStaticClassSingleton();
    }
    /**
     * 防止序列化创建,但是在jvm内部已经创建了一次，只不过会被该方法的返回值覆盖
     * {@link HungrySingleton}
     * {@link java.io.ObjectInputStream#readOrdinaryObject(boolean)}
     * {@link java.io.ObjectStreamClass#hasReadResolveMethod()}
     */
    private Object readResolve(){
        return LazyStaticClassSingletonHolder.INSTANCE;
    }
}
