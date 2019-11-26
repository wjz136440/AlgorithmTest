package Test;

/**
 * @program: ad-flink
 * @description: 数组实现队列
 * @author: joshua.Wang
 * @create: 2019-11-13 09:00
 **/
public class ArrayQueue {

    private int length = 3;
    //出队指针
    private int first = 0;
    //入队指针
    private int rear = 0;
    Object[] list = new Object[length];

    private boolean hasFull() {
        return rear == first && list[first] != null;
    }

    private boolean hasEmpty() {
        //等队列满的时候两者也相等，要判断其中是否有元素
        return rear == first && list[first] == null;
    }

    private void put(Object temp) throws Exception {
        if (hasFull()) {
            throw new Exception("has full");
        }
        //先放入数组中
        list[rear] = temp;
        rear = (rear + 1) % length;
    }

    private Object pull() throws Exception {
        if (hasEmpty()) {
            throw new Exception("has Empty");
        }
        //输出元素
        Object temp = list[first];
        //置空元素
        list[first] = null;
        //后移指针
        first = (first + 1) % length;
        return temp;
    }

    public static void main(String[] args) throws Exception {
        ArrayQueue queue = new ArrayQueue();
        queue.put(1);
        queue.put(2);
        queue.put(3);
        queue.put(4);
        System.out.println(queue.pull());
    }
}


