public class Mgr06 {
    public static volatile Mgr06 INSTANCE;

    private Mgr06() {
    }

    private static Mgr06 getInstance() {
        if (INSTANCE == null) {
            synchronized (Mgr06.class) {
                if (INSTANCE == null) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // JVM初始化指令分为三步，1、对象申请内存，2、成员变量初始化，3、内存内容赋值给引用变量；
                    // 指令重排序导致2和3顺序颠倒，
                    // 可能当前线程未运行完同步代码块，其他线程第一次非空判断的时候，判断非空，直接使用变量，读取到的值不是正确的值
                    INSTANCE = new Mgr06();
                }
            }
        }
        return INSTANCE;
    }

    public void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> System.out.println(Mgr06.getInstance().hashCode())).start();
        }
    }
}
