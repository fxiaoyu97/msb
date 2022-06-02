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
 * <p>
 * 使用reentrantlock可以使用tryLock()方法。获取到锁并返回true；获取不到并返回false
 * <p>
 * 使用Reentrantlock还可以调用lockInterruptibly方法，可以对线程调用interrupt方法，打断线程的等待锁标志的获取
 * 在一个线程等待锁的过程中，可以被打断
 */
public class T04_ReentrantLock4 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t1 start");
                for (int i = 0; i < Integer.MAX_VALUE / 10; i++) {
                    String newString = new String();
                    Math.random();
                }
                System.out.println("t1 end");
            } catch (Exception e) {
                System.out.println("t1 interrupted");
                e.printStackTrace();
            } finally {
                lock.lock();
            }
        });
        // t1.start();
        // 不会进入到catch代码块，没有异常抛出，无法终端线程的执行
        t1.interrupt();

        Thread t2 = new Thread(() -> {
            try {
                // 可以对interrupt方法做出响应
                lock.lockInterruptibly();
                System.out.println("t2 start");
                for (int i = 0; i < Integer.MAX_VALUE / 10; i++) {
                    String newString = new String();
                    Math.random();
                }
                System.out.println("t2 end");
            } catch (InterruptedException e) {
                System.out.println("t2 interrupted");
                e.printStackTrace();
            } finally {
                lock.lock();
            }
        });
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 打断t2的等待，进入异常处理catch代码块，已经开始执行的线程无法被打断
        t2.interrupt();
    }
}
