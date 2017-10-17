package com.test.main.delay.day20170927;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列 存放有效期对象
 * 
 */
public class DelayItemQueueThread {

	public ConcurrentHashMap<String, DelayOrderInvalidCustTask> map = new ConcurrentHashMap<String,DelayOrderInvalidCustTask>();
	private DelayItemQueueThread() {
		init() ;
	}

	/**
	 * 延迟加载(线程安全)
	 * 
	 */
	private static class LazyHolder {
		private static DelayItemQueueThread itemQueueThread = new DelayItemQueueThread();
	}

	public static DelayItemQueueThread getInstance() {
		return LazyHolder.itemQueueThread;
	}

	/**
	 * 缓存线程池
	 */
	ExecutorService executor = Executors.newCachedThreadPool();

	/**
	 * 线程
	 */
	private Thread daemonThread;

	class DelayThread implements Runnable {

		@Override
		public void run() {
			try {
				execute();
			} catch (InterruptedException e) {
				e.printStackTrace();  
			}  
		}
		
	}
	
	
	/**
	 * 初始化线程
	 */
	public void init()  
	    {  
		    DelayThread t = new DelayThread();
	        daemonThread = new Thread(t);
	        System.out.println("init------------------start");  
	        daemonThread.start();  
	    }
	
	private void execute() throws InterruptedException {
		while (true) {
			Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
			System.out.println("线程数--------------" + map.size());
			System.out.println("当前时间:"+System.currentTimeMillis());
			System.out.println("延迟队列数:"+queue.size());
			System.out.println("线程状态---------"
					+ Thread.currentThread().getState());
			try {
				// 从延迟队列中取值,如果没有对象过期则队列一直等待，
				DelayedItem<?> t1 = queue.take();
				if (t1 != null) {
					Runnable task = t1.getTask();
					if (task == null) {
						continue;
					}
					executor.execute(task);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建空的延迟队列
	 */
	public DelayQueue<DelayedItem<?>> queue = new DelayQueue<DelayedItem<?>>();
   


	/**
	 * 往队列中添加任务
	 * 
	 * @param time
	 *            延迟时间
	 * @param task
	 *            任务
	 * @param timeUnit
	 *            时间单位
	 * 
	 */
	public void put(long liveTime, DelayOrderInvalidCustTask task, TimeUnit timeUnit) {
		// 转换成ns
		/*long nanoTime = TimeUnit.NANOSECONDS.convert(millitime, timeUnit);
		logger.info("***DelayItemQueueThread****添加任务,订单号为"+task.oid+",任务触发时间为"+millitime+"后");
		DelayedItem<?> k = new DelayedItem(nanoTime, task);
		item.put(k);*/
		DelayOrderInvalidCustTask v2 = map.put(""+task.oid, task);
	    DelayedItem<?> tmpItem = new DelayedItem<>(liveTime,task);
	    if (v2 != null) {
	        queue.remove(tmpItem);
	    }
	    queue.put(tmpItem);
	}

	/**
	 * 结束任务
	 * 
	 * @param task
	 */
	public boolean endTask(DelayedItem<Runnable> task) {
		return queue.remove(task);
	}
	
}

