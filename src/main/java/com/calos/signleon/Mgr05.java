/**
 * lazy loading 也成懒汉式
 * 虽然达到了按需初始化的目的，但却带来线程不安全的问题
 * 可以通过synchronized解决，但也带来了效率下降
 *
 * @author calos
 * @version 1.0
 * @date 2022/5/27 07:16
 */
public class Mgr05 {
    private static Mgr05 INSTANCE;

    private Mgr05() {
    }

    private static Mgr05 getInstance() {
        if (INSTANCE == null) {
            // 通过减少同步代码块的方式提高效率，但是行不通
            // 多个线程还是会进入同步代码块，创建不同的变量，带来线程不安全的问题
            synchronized (Mgr05.class) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                INSTANCE = new Mgr05();
            }
        }
        return INSTANCE;
    }

    private void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> System.out.println(Mgr05.getInstance().hashCode())).start();
        }
    }
}
