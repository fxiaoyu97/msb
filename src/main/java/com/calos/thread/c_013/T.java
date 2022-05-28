package com.calos.thread.c_013;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile并不能保证多个线程共同修改running变量时带来的一致性问题，也就是说volatile并不能替代synchronized
 */
public class T {
    volatile int count = 0;

    void m() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        T tx = new T();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Thread(tx::m, "thread-" + i));
        }
        list.forEach(Thread::start);
        list.forEach(object -> {
            try {
                object.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(tx.count);
    }
}
