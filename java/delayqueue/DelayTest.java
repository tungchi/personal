package com.test.main.delay.day20170927;

import java.text.ParseException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DelayTest {  
	
    public static void main(String[] args) throws ParseException, InterruptedException {
    	/*DelayItemQueueThread ith=DelayItemQueueThread.getInstance();  
//        ith.init();  
        DelayOrderInvalidCustTask invalidTask = new DelayOrderInvalidCustTask(0);
        System.out.println("当前时间"+System.currentTimeMillis());
        ith.put(60*1000, invalidTask, TimeUnit.MILLISECONDS);
        System.out.println("当前时间"+System.currentTimeMillis());*/
        

        Random random = new Random();
        int cacheNumber = 10;//缓存大小
        int liveTime = 0;//存货时间
//        Cache<String, DelayOrderInvalidCustTask> cache = new Cache<String, DelayOrderInvalidCustTask>();
        DelayItemQueueThread ith=DelayItemQueueThread.getInstance();  
        for (int i = 0; i < cacheNumber; i++) {
        	DelayOrderInvalidCustTask task = new DelayOrderInvalidCustTask(i);
            liveTime = random.nextInt(3000);//存货时间3000范围以内随机
            System.out.println("订单id为"+i+",执行时间为"+liveTime+"毫秒后");
            ith.put(liveTime,task,TimeUnit.MILLISECONDS);
            if (random.nextInt(cacheNumber) > 7) {//
                liveTime = random.nextInt(3000);
                System.out.println(i+"  "+liveTime);
                ith.put(random.nextInt(liveTime),task,TimeUnit.MILLISECONDS);
            }
        }

        Thread.sleep(3000);
        System.out.println();
    
    }  
}  