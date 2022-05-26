package com.calos.signleon;

/**
 * lazy loading 也成懒汉式
 * 虽然能达到了按需初始化的目的，但是能带来线程不安全的问题
 *
 * @author calos
 * @version 1.0
 * @date 2022/5/27 07:09
 */
public class Mgr03 {
    private static Mgr03 INSTANCE;

    private Mgr03() {
    }

    public static Mgr03 getInstance() {
        if (INSTANCE == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE = new Mgr03();
        }
        return INSTANCE;
    }

    public void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> System.out.println(Mgr03.getInstance().hashCode())).start();
        }
    }
}
