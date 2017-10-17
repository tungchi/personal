package com.test.main.delay.day20170927;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class DelayedItem<T extends Runnable> implements Delayed {

	 /** 
     * 到期时间 
     */  
    private final long time;  
      
    /** 
     * 任务对象 
     */  
    private final T task;  
      
    /** 
     * 原子类 
     */  
    private static final AtomicLong atomic = new AtomicLong(0);  
      
    private final long n;  
      
    public DelayedItem(long timeout, T t)  
    {  	//这里到期时间改一下,
    	this.time = System.nanoTime() + timeout;  
        this.task = t;  
        this.n = atomic.getAndIncrement();  
    }  
      
    /** 
     * 返回与此对象相关的剩余延迟时间，以给定的时间单位表示 
     */  
    @Override
    public long getDelay(TimeUnit unit)  
    {  
        return unit.convert(this.time - System.nanoTime(), TimeUnit.NANOSECONDS);  
    }  
      
    public int compareTo(Delayed other)  
    {  
        if (other == this)  
            return 0;  
        if (other instanceof DelayedItem)  
        {  
        	DelayedItem<?> x = (DelayedItem<?>)other;  
            long diff = time - x.time;  
            if (diff < 0)  
                return -1;  
            else if (diff > 0)  
                return 1;  
            else if (n < x.n)  
                return -1;  
            else  
                return 1;  
        }  
        long d = (getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS));  
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);  
    }  
      
    public T getTask()  
    {  
        return this.task;  
    }  
      
    @Override  
    public int hashCode()  
    {  
        return task.hashCode();  
    }  
      
    @Override  
    public boolean equals(Object object)  
    {  
        if (object instanceof DelayedItem)  
        {  
            return object.hashCode() == hashCode() ? true : false;  
        }  
        return false;  
    }  

}
