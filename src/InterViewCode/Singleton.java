package InterViewCode;

// 单例模式
public class Singleton {
    // 这里必须用volatile修饰，禁止指令重排列
//    uniqueInstance = new Singleton(); 这段代码其实是分为三步执行：
//    1为 uniqueInstance 分配内存空间
//    2初始化 uniqueInstance
//    3将 uniqueInstance 指向分配的内存地址
//    但是由于 JVM 具有指令重排的特性，执行顺序有可能变成 1->3->2。指令重排在单线程环境下不会出现问题，但是在多线程环境下会导致一个线程获得还没有初始化的实例。
//    例如，线程 T1 执行了 1 和 3，此时 T2 调用 getUniqueInstance() 后发现 uniqueInstance 不为空，因此返回 uniqueInstance，但此时 uniqueInstance 还未被初始化。
    private volatile static Singleton uniqueInstance;

    private Singleton() {
    }

    public  static Singleton getUniqueInstance() {
        // 双重校验锁
        // 先判断对象是否已经实例过，没有实例化过才进入加锁代码
//        1. 第一次if去掉：在线程执行的安全性上没有问题，但是去掉以后变成串行，全部线程都要经过syn的加解锁，效率低下。
//        2. 第二次if去掉：假设两个线程同时走到syn前，如果有一个线程先进入了同步语句，new之后就会推出syn保护区域；此时第二个线程拿到锁也new，破坏了单例模式。
        if (uniqueInstance == null) {
            //类对象加锁
            synchronized (Singleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }

}
