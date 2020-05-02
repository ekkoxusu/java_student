import com.su.example.container.ContatinerSingleton;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 单例模式
 * 优点：在内存中只有一个实例,减少内存开销,避免资源占用,全局只有一个访问点,严格控制入口
 * 缺点：没有接口,扩展很困难,想扩展只能改代码,不符合开闭原则
 */
public class TestSingle {

    public static final String SAVE_FILE_PATH = "/Users/su/Desktop/";

    public static void main(String[] args) {
        // 测试多线程
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 2000, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(10), new ThreadPoolExecutor.CallerRunsPolicy());
//        for(int i= 0;i<10;i++){
//            threadPoolExecutor.execute(()->
//                System.out.println(LazyStaticClassSingleton.getInstance())
//            );
//        }
//        threadPoolExecutor.shutdown();
//        LazyStaticClassSingleton instance = LazyStaticClassSingleton.getInstance();
        // 反射破解
//        Class<LazyStaticClassSingleton> lazyClazz = LazyStaticClassSingleton.class;
//        try {
//            Constructor<LazyStaticClassSingleton> con = lazyClazz.getDeclaredConstructor();
//            con.setAccessible(true);
//            LazyStaticClassSingleton classToinstance = con.newInstance();
//            System.out.println(classToinstance);
//            System.out.println(instance);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        // 反序列化破解
//        ObjectOutputStream oos = null;
//        ObjectInputStream ois = null;
//        try {
//            oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH + "test.txt"));
//            oos.writeObject(instance);
//            oos.flush();
//            oos.close();
//            ois = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH + "test.txt"));
//            LazyStaticClassSingleton serializationInstance = (LazyStaticClassSingleton) ois.readObject();
//            System.out.println(instance);
//            System.out.println(serializationInstance);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        for(int i= 0;i<10;i++){
            threadPoolExecutor.execute(()->
                System.out.println(
                        ContatinerSingleton.getSingleton(
                                "com.su.example.container.ContatinerSingletonTestDemo"))
            );
        }
        threadPoolExecutor.shutdown();

    }

}
