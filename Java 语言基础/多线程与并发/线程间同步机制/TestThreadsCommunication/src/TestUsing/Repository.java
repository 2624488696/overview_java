package TestUsing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walker on 2018/9/4.
 */
public class Repository {
    private List<Integer> list = new ArrayList<>();
    private int limit = 10;  //���òֿ���������

    public synchronized void addGoods(int count) throws InterruptedException {
        while(list.size() == limit){
            //�ﵽ�ֿ����ޣ����ܼ�������
            wait();
        }
        list.add(count);
        System.out.println("������������Ʒ��" + count);
        //֪ͨ���е�������
        notifyAll();
    }

    public synchronized void removeGoods() throws InterruptedException {
        while(list.size() <= 0){
            //�ֿ���û�в�Ʒ
            wait();
        }

        int res = list.get(0);
        list.remove(0);
        System.out.println("���������Ѳ�Ʒ��" + res);
        //֪ͨ���е�������
        notifyAll();
    }
}
