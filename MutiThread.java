package Test;

import java.util.concurrent.CountDownLatch;

/**
 * @program: ad-flink
 * @description:
 * @author: joshua.Wang
 * @create: 2019-11-18 11:41
 **/
public class MutiThread {

    /**
     * 3个线程同时开始，有一个线程异常则立即停止，等所有线程都成功后返回
     *
     * @param
     */
    public static void main(String[] args) throws Exception{

        CountDownLatch c1 = new CountDownLatch(3);
        CountDownLatch c2 = new CountDownLatch(3);



        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("线程1准备！");
                    //将计数器减一
                    c1.countDown();
                    //再等待
                    c1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程1开始！");
                c2.countDown();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("线程2准备！");
                    c1.countDown();
                    c1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程2开始！");
                c2.countDown();
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    System.out.println("线程3准备！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                c1.countDown();
                System.out.println("线程3开始！");
                c2.countDown();
            }
        });


        thread1.start();
        thread2.start();
        thread3.start();
        c2.await();
        System.out.println("main线程结束！");


    }
}
