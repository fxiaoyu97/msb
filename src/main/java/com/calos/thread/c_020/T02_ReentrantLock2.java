package com.calos.thread.c_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * reentrantlock用于替代synchronized
 * 本例中由于m1锁定this，只有m1执行完毕的时候，m2才能执行
 * 这里是复习synchronized最原始的语义
 * <p>
 * 使用reentrantlock可以完成同样的功能
 * 需要注意的是，必须要手动释放锁
 * 使用sys锁定的话，如果遇到异常，jvm会自动释放锁，但是lock必须手动释放，因此经常忘记释放锁
 */
public class T02_ReentrantLock2 {
    Lock lock = new ReentrantLock();

    void m1() {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    synchronized void m2() {
        try {
            lock.lock();
            System.out.println("m2...");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        T02_ReentrantLock2 t = new T02_ReentrantLock2();
        new Thread(t::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(t::m2).start();
    }
}
