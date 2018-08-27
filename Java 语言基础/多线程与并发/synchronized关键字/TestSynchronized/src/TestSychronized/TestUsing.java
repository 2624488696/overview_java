package TestSychronized;

import org.junit.Test;

/**
 * Created by Walker on 2018/8/20.
 */
public class TestUsing {

    private static int count;
    private Object obj = new Object();

    @Test
    public void test() throws InterruptedException {
        //���� 100 ���߳�
        ThreadOne[] threads = new ThreadOne[1000];
        for (int i=0; i<100; i++){
            threads[i] = new ThreadOne();
            threads[i].start();
        }
        //���̵߳ȴ����и��߳�ִ�н���
        for (int j=0; j<100; j++){
            threads[j].join();
        }
        System.out.println(count);

    }

    class ThreadOne extends Thread{
        @Override
        public void run(){
            try {
                //ģ����߳��������
                //ÿ���߳�������������� [0 - 200ms]
                this.sleep((long) (Math.random() * 200));
                //������Ϊ count ��һ
                synchronized (obj){
                    count++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
