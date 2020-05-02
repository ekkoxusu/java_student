# 单例模式
### 单例模式简介
单例模式是一个类绝对只有一个实例,并提供全局访问点,隐藏构造方法,属于创作模式 
### 单例模式的应用场景
* 优点: 在内存中只有一个实例,减少内存开销,避免资源占用,全局只有一个访问点,严格控制入口
* 缺点: 没有接口,扩展很困难,想扩展只能改代码,不符合开闭原则
### 反射暴力破解单例模式
```java
        LazyStaticClassSingleton instance = LazyStaticClassSingleton.getInstance();
         //反射破解
        Class<LazyStaticClassSingleton> lazyClazz = LazyStaticClassSingleton.class;
        try {
            Constructor<LazyStaticClassSingleton> con = lazyClazz.getDeclaredConstructor();
            con.setAccessible(true);
            LazyStaticClassSingleton classToinstance = con.newInstance();
            System.out.println(classToinstance);
            System.out.println(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
```
**解决方案(以静态内部类为例)**
```java
    // 私有化构造方法，解决反射创建
    private LazyStaticClassSingleton() {
        if(LazyStaticClassSingletonHolder.INSTANCE != null){
            throw new RuntimeException("不允许再次创建");
        }
    }
```
### 序列化破解单例模式
```java
        LazyStaticClassSingleton instance = LazyStaticClassSingleton.getInstance();
        //反序列化破解
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH + "test.txt"));
            oos.writeObject(instance);
            oos.flush();
            oos.close();
            ois = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH + "test.txt"));
            LazyStaticClassSingleton serializationInstance = (LazyStaticClassSingleton) ois.readObject();
            System.out.println(instance);
            System.out.println(serializationInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
```
**解决方案(以静态内部类为例)**
```java
    /**
     * 防止序列化创建,但是在jvm内部已经创建了一次，只不过会被该方法的返回值覆盖
     * {@link HungrySingleton}
     * {@link java.io.ObjectInputStream#readOrdinaryObject(boolean)}
     * {@link java.io.ObjectStreamClass#hasReadResolveMethod()}
     */
    private Object readResolve(){
        return LazyStaticClassSingletonHolder.INSTANCE;
    }
```
### 常见的单例模式
##### 饿汉式单例
示例:
```java
public class HungrySingleton {
    // 私有化构造方法
    private HungrySingleton(){}
    // 在类加载的同时创建该元素
    private final static HungrySingleton INSTANCE = new HungrySingleton();

    public static HungrySingleton getInstance(){
        return INSTANCE;
    }
}
```
* 优点：保证线程安全,以空间换取时间
* 缺点：还未使用就创建浪费内存空间
##### 懒汉式单例
###### 双重校验锁
示例:
```java
/**
 * 懒汉式双重校验锁
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
```
* 优点：使用时创建,时间换取空间
* 缺点：使用了 synchronized 不可避免的性能问题
###### 静态内部类
示例:
```java
/**
 * 懒汉式静态内部类
 */
public class LazyStaticClassSingleton{

    // 私有化构造方法，解决反射创建
    private LazyStaticClassSingleton() {
        if(LazyStaticClassSingletonHolder.INSTANCE != null){
            throw new RuntimeException("不允许再次创建");
        }
    }
    public static LazyStaticClassSingleton getInstance(){

        return LazyStaticClassSingletonHolder.INSTANCE;
    }
    private static class LazyStaticClassSingletonHolder{
        private final static LazyStaticClassSingleton INSTANCE = new LazyStaticClassSingleton();
    }
}
```

* 优点：使用时创建

* 缺点：因为使用静态内部类创建，无可避免的无法将参数传入

**静态内部类为什么是线程安全的且在使用的时候创建**
类加载时机：JAVA虚拟机在有且仅有的5种场景下会对类进行初始化。
1.遇到new、getstatic、setstatic或者invokestatic这4个字节码指令时，对应的java代码场景为：new一个关键字或者一个实例化对象时、读取或设置一个静态字段时
(final修饰、已在编译期把结果放入常量池的除外)、调用一个类的静态方法时。
2.使用java.lang.reflect包的方法对类进行反射调用的时候，如果类没进行初始化，需要先调用其初始化方法进行初始化。
3.当初始化一个类时，如果其父类还未进行初始化，会先触发其父类的初始化。
4.当虚拟机启动时，用户需要指定一个要执行的主类(包含main()方法的类)，虚拟机会先初始化这个类。
5.当使用JDK 1.7等动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后的解析结果REF_getStatic、REF_putStatic、REF_invokeStatic
的方法句柄，并且这个方法句柄所对应的类没有进行过初始化，则需要先触发其初始化。
这5种情况被称为是类的主动引用，注意，这里《虚拟机规范》中使用的限定词是"有且仅有"，那么，除此之外的所有引用类都不会对类进行初始化，称为被动引用。静态内部类就属于被动引用的行列。
虚拟机会保证一个类的<clinit>()方法在多线程环境中被正确地加锁、同步，如果多个线程同时去初始化一个类，那么只会有一个线程去执行这个类的<clinit>()方法，其他线程都需要阻塞等待，直到活动线程执行<clinit>()方法完毕
##### 注册式单例
###### 枚举式
示例:  
```java
/**
 * 容器式单例枚举

 */
public enum SingletonEnum {
    INSTANCE;
}
```
* 优点：统一管理，天然免疫序列化和反射破坏单例
* 缺点：无法动态传入参数，无法继承
###### 容器式单例
示例:
```java


/**
- 容器式单例
- 此处是简单实现，实际深度实现参考{@link AbstractBeanFactory}
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

```
##### 线程单例
示例:
```java
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
```
* 优点：实现每一个线程之间都是单例的(暂时没想到应用场景)
* 缺点：无法动态传参
### Spring自身如何实现Bean单例并解决循环依赖问题
[请看YourBatman文章](https://blog.csdn.net/f641385712/article/details/92801300)
此处引入文字宏观流程
> 依旧以上面A、B类使用属性field注入循环依赖的例子为例，对整个流程做文字步骤总结如下：

1. 使用context.getBean(A.class)，旨在获取容器内的单例A(若A不存在，就会走A这个Bean的创建流程)，显然初次获取A是不存在的，因此走A的创建之路~
2. 实例化A（注意此处仅仅是实例化），并将它放进缓存（此时A已经实例化完成，已经可以被引用了）
3. 初始化A：@Autowired依赖注入B（此时需要去容器内获取B）
4. 为了完成依赖注入B，会通过getBean(B)去容器内找B。但此时B在容器内不存在，就走向B的创建之路~
5. 实例化B，并将其放入缓存。（此时B也能够被引用了）
6. 初始化B，@Autowired依赖注入A（此时需要去容器内获取A）
7. 此处重要：初始化B时会调用getBean(A)去容器内找到A，上面我们已经说过了此时候因为A已经实例化完成了并且放进了缓存里，所以这个时候去看缓存里是已经存在A的引用了的，所以getBean(A)能够正常返回
8. B初始化成功（此时已经注入A成功了，已成功持有A的引用了），return（注意此处return相当于是返回最上面的getBean(B)这句代码，回到了初始化A的流程中~）。
9. 因为B实例已经成功返回了，因此最终A也初始化成功
10. 到此，B持有的已经是初始化完成的A，A持有的也是初始化完成的B，完美~

PS: [csdn](https://blog.csdn.net/weixin_38423383/article/details/105894823)