package com.test.main.day20171013;

import java.math.BigDecimal;

public class HannoiTest {
	static int plates_nums = 3;
	static int mv_times = 0;
	static int fail_time;
	static HannoiStack HannoiA= new HannoiStack(plates_nums,"HannoiA",-2);
	static HannoiStack HannoiB= new HannoiStack(plates_nums,"HannoiB",0);
	static HannoiStack HannoiC= new HannoiStack(plates_nums,"HannoiC",2);
	public static void initHannoiStack() throws Exception{
		for(int i=plates_nums;i>0;i--){
			HannoiA.push(i);
		}
	}
	public static void main(String[] args) throws Exception {
		initHannoiStack();
		movePlates(HannoiA, HannoiC, HannoiB);
	}
	
	public static HannoiStack getSrc(HannoiStack HannoiA,HannoiStack HannoiB,HannoiStack HannoiC){
		if(HannoiA.type==0){
			return HannoiA;
		}
		else if(HannoiB.type==0){
			return HannoiB;
		}
		else{
			return HannoiC;
		}
	}
	public static HannoiStack getDst(HannoiStack HannoiA,HannoiStack HannoiB,HannoiStack HannoiC){
		if(HannoiA.type==1){
			return HannoiA;
		}
		else if(HannoiB.type==1){
			return HannoiB;
		}
		else{
			return HannoiC;
		}
	}
	
	public static HannoiStack getTemp(HannoiStack HannoiA,HannoiStack HannoiB,HannoiStack HannoiC){
		if(HannoiA.type==2){
			return HannoiA;
		}
		else if(HannoiB.type==2){
			return HannoiB;
		}
		else{
			return HannoiC;
		}
	}
	
	public static void movePlates(HannoiStack src,HannoiStack dst,HannoiStack temp) throws Exception{
		resetSrcDstAndTemp(src, dst, temp);
		if(!q1(src, dst, temp)){
			if(!q2(src, dst, temp)){
				q3(src, dst, temp);
			}
		}
		mv(getMinWeightHannoiStack(src,dst,temp), getMaxWeightHannoiStack(src, dst, temp));
		if(src.getSum()!=0||temp.getSum()!=0){
			movePlates(src, dst, temp);
		} 
	}
	private static void resetSrcDstAndTemp(HannoiStack src, HannoiStack dst,
			HannoiStack temp) {
		HannoiStack tempSrc = getSrc(src, dst, temp);
		HannoiStack tempDst = getDst(src, dst, temp);
		HannoiStack tempTemp = getTemp(src, dst, temp);
		src = tempSrc;
		dst = tempDst;
		temp = tempTemp;
	}
	private static HannoiStack getMinWeightHannoiStack(HannoiStack src,
			HannoiStack dst, HannoiStack temp) {
		if(src.getWeight()<=dst.getWeight()&&src.getWeight()<=temp.getWeight()){
			System.out.println("最小权重src:" + src.getWeight());
			return src;
		}else if(dst.getWeight()<=src.getWeight()&&dst.getWeight()<=temp.getWeight()){
			System.out.println("最小权重dst:" + dst.getWeight());
			return dst;
		}else{
			System.out.println("最小权重temp:" + temp.getWeight());
			return temp;
		}
	}
	private static HannoiStack getMaxWeightHannoiStack(HannoiStack src,
			HannoiStack dst, HannoiStack temp) {
		if(src.getWeight()>=dst.getWeight()&&src.getWeight()>=temp.getWeight()){
			System.out.println("最大权重src:" + src.getWeight());
			return src;
		}else if(dst.getWeight()>=src.getWeight()&&dst.getWeight()>=temp.getWeight()){
			System.out.println("最大权重dst:" + dst.getWeight());
			return dst;
		}else{
			System.out.println("最大权重temp:" + temp.getWeight());
			return temp;
		}
	}
	public static boolean mv(HannoiStack src,HannoiStack dst) throws Exception{
		boolean rs = false;
		int plate = src.pop();
		dst.push(plate);
		rs = true;
		return rs;
	}
	public static int compare(HannoiStack hannoi1,HannoiStack hannoi2){
		if(hannoi2.getTopPlate()==BigDecimal.ZERO.intValue()){
			return 0;
		}
		if(hannoi1.getTopPlate()>hannoi2.getTopPlate()){
			return 1;
		}else if(hannoi1.getTopPlate()==hannoi2.getTopPlate()){
			return 0;
		}else{
			return -1;
		}
	}
	public static void compareAndCalculateWeight(HannoiStack hannoi1,HannoiStack hannoi2){
		if(hannoi1.getTopPlate()>hannoi2.getTopPlate()){
			hannoi1.addWeight(2);
			hannoi2.addWeight(-2);
		}else if(hannoi1.getTopPlate()==hannoi2.getTopPlate()){
			
		}else{
			hannoi1.addWeight(-2);
			hannoi2.addWeight(2);
		}
	}
	public static void calCulateWeight(HannoiStack src,HannoiStack dst,HannoiStack temp){
		resetHannoiStackWeight(src, dst, temp);
		switch(getMaxHannoiStackSum(src, dst, temp)){
			case 1:src.subWeight(1); break;
			case 2:dst.subWeight(1);break;
			case 3:temp.subWeight(1);break;
		}
		switch(getMostHannoiStackPlates(src, dst, temp)){
			case 1:src.subWeight(1); break;
			case 2:dst.subWeight(1);break;
			case 3:temp.subWeight(1);break;
		}
		if(dst.getSum()==BigDecimal.ZERO.intValue()){
			dst.addWeight(2);
		}
		if(temp.getSum()==BigDecimal.ZERO.intValue()){
			dst.addWeight(2);
		}
		compareAndCalculateWeight(src, dst,temp);
		compareAndCalculateWeight(src, temp);
		compareAndCalculateWeight(dst, temp);
		
	}
	private static void compareAndCalculateWeight(HannoiStack src,
			HannoiStack dst, HannoiStack temp) {
		if(src.getTopPlate()>dst.getTopPlate()){
			src.addWeight(1);
			dst.addWeight(-5);
		}else if(src.getTopPlate()==dst.getTopPlate()){
		}else{
			src.addWeight(-1);
			dst.addWeight(5);
		}
		
		if(dst.getTopPlate()>temp.getTopPlate()){
			dst.addWeight(5);
			temp.addWeight(-5);
		}else if(dst.getTopPlate()==temp.getTopPlate()){
		}else{
			dst.addWeight(-1);
			temp.addWeight(5);
		}
	}
	public static void resetHannoiStackWeight(HannoiStack src,HannoiStack dst,HannoiStack temp){
		src.resetWeight();
		dst.resetWeight();
		temp.resetWeight();
	}
	public static int getMaxHannoiStackSum(HannoiStack src,HannoiStack dst,HannoiStack temp){
		HannoiStack maxHannoiStack = src;
		int no = 1;
		if(maxHannoiStack.getSum()<dst.getSum()){
			maxHannoiStack = dst;
			no = 2;
		}
		if(maxHannoiStack.getSum()<temp.getSum()){
			maxHannoiStack = temp;
			no = 3;
		}
		return no;
	}
	public static int getMostHannoiStackPlates(HannoiStack src,HannoiStack dst,HannoiStack temp){
		HannoiStack maxHannoiStack = src;
		int no = 1;
		if(maxHannoiStack.getPlateNums()<dst.getPlateNums()){
			maxHannoiStack = dst;
			no = 2;
		}
		if(maxHannoiStack.getPlateNums()<temp.getPlateNums()){
			maxHannoiStack = temp;
			no = 3;
		}
		return no;
	}
	
	
	
	
	
	
	/**
	 * 
	 * 各种权重因子,第一次推
	 */
	public static boolean q1(HannoiStack src,HannoiStack dst,HannoiStack temp){
		boolean rs = false;
		if(src.idx==0){
			src.addWeight(-999);
			if(src.high%2==0){
				temp.addWeight(999);
			}else{
				dst.addWeight(999);
			}
			rs =true;
		}
		return rs;  
	}
	
	/**
	 * 如果src只剩最后一个,&& dst一个都没有,dst.idx = high,
	 * @param src
	 * @param dst
	 * @param temp
	 */
	public static boolean q2(HannoiStack src,HannoiStack dst,HannoiStack temp){
		boolean rs = false;
		if(src.idx==src.high-1&&dst.idx==dst.high&&temp.idx!=temp.high){
			src.addWeight(-999);
			dst.addWeight(999);
			rs =true;
		}
		return rs;  
	}
	
	/**
	 * 如果src已经不能pop了,那么把要把dst,temp里的最大值当作最大值,
	 * @param src
	 * @param dst
	 * @param temp
	 */
	public static void q3(HannoiStack src,HannoiStack dst,HannoiStack temp){
		if(src.idx==0){
			return;
		}
		if(src.idx==src.high){
			int srcweight = src.getWeight();
			src.setWeight(temp.getWeight());
			temp.setWeight(srcweight);
		}
		if(src.getTopPlate()>dst.getTopPlate()&&src.getTopPlate()>temp.getTopPlate()){
			int srcweight = src.getWeight();
			src.setWeight(chargeMaxPlateHannoi(dst,temp).getWeight());
			chargeMaxPlateHannoi(dst,temp).setWeight(srcweight);
		}
	}
	private static HannoiStack chargeMaxPlateHannoi(HannoiStack dst,
			HannoiStack temp) {
		if(dst.getMaxPlate()>temp.getMaxPlate()){
			return dst;
		}else{
			return temp;
		}
	}
}
