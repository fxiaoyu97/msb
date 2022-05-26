package com.calos.signleon;

/**
 * 饿汉式
 * 类加载到内存后，就实例化一个单利，JVM保证线程安全
 * 简单实用，推荐使用
 * 唯一缺点：不管有没有用到，类加载时就完成实例化
 * Class.forName("") 话说你用不到的，你装载它干什么
 *
 * @author calos
 * @version 1.0
 * @date 2022/5/27 07:04
 */
public class Mgr01 {
    private static final Mgr01 INSTANCE = new Mgr01();

    private Mgr01() {
    }

    public static Mgr01 getInstance() {
        return INSTANCE;
    }

    public void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
        Mgr01 m1 = Mgr01.getInstance();
        Mgr01 m2 = Mgr01.getInstance();
        System.out.println(m1 == m2);
    }
}
