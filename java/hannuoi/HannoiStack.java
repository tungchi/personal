package com.test.main.day20171013;

import java.math.BigDecimal;



public class HannoiStack {
	String hannoiStackName ;
	int high;
	int maxhigh ;//最多可以放盘子?个
	int idx;
	int[] plates;
	int weight;//权重因子,用来判断搬运到哪个汉诺塔,权重因子越大,则越要把盘子搬运到该塔
	int finalWeight;
	int sum ;
	int type ;//0为src,1为temp,2为dst
	int maxPlate;
	
	public int getMaxPlate(){
		if(idx==high){
			return 0 ;
		}else{
			return plates[high-1];
		}
	}
	
	public int getPlateNums(){
		return this.high-this.idx;
	}
	
	public void resetWeight(){//重置权重因子
		this.weight = this.finalWeight;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int addWeight(int weight) {
		this.weight = this.weight + weight;
		return this.weight;
	}
	
	public int subWeight(int weight) {
		this.weight = this.weight - weight;
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getSum(){
		return this.sum;
	}

	public HannoiStack(int high,String hannoiStackName,int finalWeight){
		this.plates = new int[high];
		this.high = high;
		this.idx = high;
		this.hannoiStackName = hannoiStackName;
		this.finalWeight = finalWeight;
		this.weight = finalWeight;
		this.sum = BigDecimal.ZERO.intValue();
	}
	
	public int getTopPlate(){
		if(idx==high){
			return BigDecimal.ZERO.intValue();
		}
		return plates[idx];
	}
	
	public int pop(){
		if(this.sum==0){
			return 0;
		}
		int plate = plates[idx];
		plates[idx] = BigDecimal.ZERO.intValue();
		idx = idx+1;
		this.sum = this.sum-plate;
		this.display();
		return plate;
	}
	public void push(int plate) throws Exception{
		charge(plate);
		plates[idx-1] = plate;
		idx = idx-1;
		this.sum = this.sum+plate;
		this.display();
	}
	private void charge(int plate) throws Exception {
		if(idx==high){
			return;
		}else{
			if(plates[idx]<plate){
				throw new Exception("汉诺塔"+hannoiStackName+"第"+idx+"层的盘子是:"+plates[idx]+",要放入的盘子是"+plate+",放置失败!");
			}
		}
	}

	public void display(){
		StringBuffer sb = new StringBuffer();
		sb.append("汉诺塔"+hannoiStackName);
		for(int i=1;i<plates.length+1;i++){
			sb.append("第"+i+"层的盘子是:"+plates[i-1]+",");
		}
		System.out.println(sb.toString().substring(0, sb.length()-1));
	}
}
