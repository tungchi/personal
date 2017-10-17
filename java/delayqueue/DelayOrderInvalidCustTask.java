package com.test.main.delay.day20170927;


public class DelayOrderInvalidCustTask implements Runnable{
	int oid;  
	String tasktime;
    public DelayOrderInvalidCustTask(int oid){  
        this.oid=oid;  
    } 
    /**
     * 
     * @param oid 订单id
     * @param tasktime 预计回单时间
     */
    public DelayOrderInvalidCustTask(int oid,String tasktime){  
        this.oid=oid;  
        this.tasktime = tasktime;
    } 
	@Override
	public void run() {
		System.out.println("执行定时任务,oid为"+oid);
	}

}
