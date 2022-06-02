package com.calos.thread.c_020;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Reentrantlock 还可以指定为公平锁
 */
public class T05_ReentrantLock5 extends Thread {
    // 参数为true表示为公平锁，对比输出结果
    private static final ReentrantLock lock = new ReentrantLock(true);

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() );
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        T05_ReentrantLock5 rl = new T05_ReentrantLock5();
        Thread t1 = new Thread(rl);
        Thread t2 = new Thread(rl);
        Thread t3 = new Thread(rl);
        t1.start();
        t2.start();
        t3.start();
    }
}
