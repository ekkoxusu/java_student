package com.su.example.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 容器式单例--超纲(Spring IOC原理)
 * 此处是简单实现，实际深度实现参考{@link AbstractBeanFactory}
 * 引入SpringBoot后观看此注释
 * 以{@link AbstractBeanFactory}为例
 * 使用3级缓存解决循环依赖问题
 *   不可解决的情况
 *   -- 构造器循环依赖
 *   -- prototype（多例）循环依赖
 *   {@link AbstractBeanFactory#singletonObjects}用于存放完全初始化好的 bean，从该缓存中取出的 bean 可以直接使用
 *   {@link AbstractBeanFactory#earlySingletonObjects}提前曝光的单例对象的cache，存放原始的 bean 对象（尚未填充属性），用于解决循环依赖
 *   {@link AbstractBeanFactory#singletonFactories}单例对象工厂的cache，存放 bean 工厂对象，用于解决循环依赖
 *   先从一级缓存singletonObjects中去获取。（如果获取到就直接return）
 *   如果获取不到或者对象正在创建中（isSingletonCurrentlyInCreation()），那就再从二级缓存earlySingletonObjects中获取。（如果获取到就直接return）
 *   如果还是获取不到，且允许singletonFactories（allowEarlyReference=true）通过getObject()获取。就从三级缓存singletonFactory.getObje
 *   获取。（如果获取到了就从singletonFactories中移除，并且放进earlySingletonObjects。
 *   从三级缓存剪切到了二级缓存
 *   如果当前对象正在创建则会放入 singletonsCurrentlyInCreation(collections.newSetFromMap(new ConcurrentHashMap<>(16)))
 *   使用context.getBean(A.class)，旨在获取容器内的单例A(若A不存在，就会走A这个Bean的创建流程)，显然初次获取A是不存在的，因此走A的创建之路~
 *   实例化A（注意此处仅仅是实例化），并将它放进缓存（此时A已经实例化完成，已经可以被引用了）
 *   初始化A：@Autowired依赖注入B（此时需要去容器内获取B）
 *   为了完成依赖注入B，会通过getBean(B)去容器内找B。但此时B在容器内不存在，就走向B的创建之路~
 *   实例化B，并将其放入缓存。（此时B也能够被引用了）
 *   初始化B，@Autowired依赖注入A（此时需要去容器内获取A）
 *   此处重要：初始化B时会调用getBean(A)去容器内找到A，上面我们已经说过了此时候因为A已经实例化完成了并且放进了缓存里，所以这个时候去看缓存里是已经存在A的引用了的，所以getBean(A)能够正常返回
 *   B初始化成功（此时已经注入A成功了，已成功持有A的引用了），return（注意此处return相当于是返回最上面的getBean(B)这句代码，回到了初始化A的流程中~）。
 *   因为B实例已经成功返回了，因此最终A也初始化成功
 *   到此，B持有的已经是初始化完成的A，A持有的也是初始化完成的B
 */
public class ContatinerSingleton {

    private final static Map<String, Object> STRING_OBJECT_CONCURRENT_HASH_MAP = new ConcurrentHashMap<>(256);

    // 此处实现并不能解决循环依赖，简单实现容器式单例的逻辑
    public static final Object getSingleton(String classFullName){
        Object singletonObject = STRING_OBJECT_CONCURRENT_HASH_MAP.get(classFullName);
        if(singletonObject == null){
            synchronized (STRING_OBJECT_CONCURRENT_HASH_MAP) {
                if((singletonObject = STRING_OBJECT_CONCURRENT_HASH_MAP.get(classFullName)) == null) {
                    try {
                        Class<?> clazz = Class.forName(classFullName);
                        singletonObject = clazz.newInstance();
                        STRING_OBJECT_CONCURRENT_HASH_MAP.put(classFullName, singletonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return singletonObject;
    }
}
