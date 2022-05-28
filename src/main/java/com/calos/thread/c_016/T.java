package com.calos.thread.c_016;

import java.util.concurrent.TimeUnit;

/**
 * synchronized优化
 * 同步代码快中的语句越少越好
 * 比较m1和m2
 * <p>
 * 如果方法业务代码中多处加锁，可以考虑扩大加锁范围，加到方法上
 */
public class T {
    int count = 0;

    synchronized void m1() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 业务逻辑中只有下面这句话需要sync，这时候不应该给整个方法上锁
        count++;

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void m2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 业务逻辑中只有下面这句话需要sync，这时候不应该给整个方法上锁
        // 采用细粒度的锁，可以使线程征用时间变短，从而提高效率
        count++;

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
