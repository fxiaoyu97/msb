package com.calos.thread.c_018;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 解决同样的问题的更高效的方法，使用AtomXXX类，基于Unsafe实现
 * 这些类都是使用CAS操作保证线程安全的
 * AtomXXX类身方法是原子性的，但不能保证多个方法连续调用是原子性的
 * <p>
 * 实现操作 Compare And Set
 * cas(V,Expected,NewValue) V-要修改的变量，Expected-期望要修改的变量值是多少，NewValue-要设定的新值
 * - if V==E
 * V = NewValue
 * otherwise try again or fail
 * - CPU原语支持，因为CAS操作是CPU指令级别支持的操作，中间不能被打断，所有在 if V==E 的过程中其他线程不能更改值
 * <p>
 * ABA问题，使用版本号解决。
 * <p>
 * 如果是基础类型，无所谓
 * 如果是指向对象，其他线程把对象内部实现修改了，这是因为指向的对象没有改变，所有检测不到，这种情况还是有可能会出现问题的
 *
 *
 */
public class T {
    AtomicInteger count = new AtomicInteger();

    void m() {
        for (int i = 0; i < 10000; i++) {
            count.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        T t = new T();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m, "thread-" + i));
        }
        threads.forEach(Thread::start);
        threads.forEach(object -> {
            try {
                object.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t.count);
    }
}
