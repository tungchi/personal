package com.test.main.day20171013;

public class SimpleHannoiTest {
	static int move_times = 0;
	public static void main(String[] args) {
		int number =4;
		String src = "a";
		String dst = "c";
		String temp = "b";
		move(src, dst, temp, number);
		System.out.println("总共搬了"+move_times+"次");
	}
	
	public static void move(String src,String dst,String temp,int number){
		if(number==1){
			move_times = move_times+1;
			move(src, dst, number);
		}else{
			move(src, temp, dst, number-1);
			move_times = move_times+1;
			move(src, dst, number);
			move(temp, dst, src, number-1);
		}
	}
	
	public static void move(String src,String dst,int number){
		System.out.println("move "+src+" ."+number+" to "+dst);
	}
}
