package LockTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Walker on 2018/10/8.
 */
public class Main {
    /**
     * Ѹ�ױ����⣺
     * <p>
     * ��дһ�����򣬿���3���̣߳���3���̵߳�ID�ֱ�ΪA��B��C,
     * ÿ���߳̽��Լ���ID����Ļ�ϴ�ӡ10�飬
     * Ҫ�����������밴ABC��˳����ʾ���磺ABCABC�����ε��ơ�
     */
    @Test
    public void test() throws InterruptedException {
        Object obj = new Object();
        List<String> list = new ArrayList<>();
        Thread threadA = new Thread() {
            @Override
            public void run() {
                int count = 0;
                synchronized (obj) {
                    while (count < 10) {
                        if (list.size() == 0 || "C".equals(list.get(list.size() - 1))) {
                            list.add("A");
                            count++;
                            obj.notifyAll();
                        } else {
                            try {
                                obj.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        Thread threadB = new Thread() {
            @Override
            public void run() {
                int count = 0;
                synchronized (obj) {
                    while (count < 10) {
                        if (list.size() != 0 && "A".equals(list.get(list.size() - 1))) {
                            list.add("B");
                            count++;
                            obj.notifyAll();
                        } else {
                            try {
                                obj.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        Thread threadC = new Thread() {
            @Override
            public void run() {
                int count = 0;
                synchronized (obj) {
                    while (count < 10) {
                        if (list.size() != 0 && "B".equals(list.get(list.size() - 1))) {
                            list.add("C");
                            count++;
                            obj.notifyAll();
                        } else {
                            try {
                                obj.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        threadA.start();
        threadB.start();
        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();

        for (String str : list) {
            System.out.print(str);
        }
    }

    @Test
    public void test2() throws InterruptedException {
        Lock lock = new ReentrantLock(true);
        List<String> list = new ArrayList<>();
        Thread threadA = new Thread(){
            @Override
            public void run(){
                int count = 0;
                while(count < 10){
                    lock.lock();
                    if (list.size() == 0 || "C".equals(list.get(list.size() - 1))){
                        list.add("A");
                        count++;
                        lock.unlock();
                    }else{
                        lock.unlock();
                    }
                }

            }
        };

        Thread threadB = new Thread(){
            @Override
            public void run(){
                int count = 0;
                while(count < 10){
                    lock.lock();
                    if (list.size() != 0 && "A".equals(list.get(list.size() - 1))){
                        list.add("B");
                        count++;
                        lock.unlock();
                    }else{
                        lock.unlock();
                    }
                }
            }
        };

        Thread threadC = new Thread(){
            @Override
            public void run(){
                int count = 0;
                while(count < 10){
                    lock.lock();
                    if (list.size() != 0 && "B".equals(list.get(list.size() - 1))){
                        list.add("C");
                        count++;
                        lock.unlock();
                    }else{
                        lock.unlock();
                    }
                }
            }
        };
        threadA.start();
        threadB.start();
        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();

        for (String str : list) {
            System.out.print(str);
        }

    }
}
