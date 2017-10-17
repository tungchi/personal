package com.test.main.day20171013;

public class Test {
	public static void main(String[] args) {
		HannoiStack h = new HannoiStack(2, "111", -1);
		System.out.println(h.hannoiStackName);
		HannoiStack r = getH(h);
		System.out.println(r.getWeight());
		System.out.println(h.getWeight());
	}

	private static HannoiStack getH(HannoiStack h) {
		HannoiStack a = h;
		a.setWeight(123);
		return a ;
	}
}
