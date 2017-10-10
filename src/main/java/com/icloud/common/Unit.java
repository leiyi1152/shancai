package com.icloud.common;

public enum Unit {
	yuan("元"),
	fen("分"),
	zhang("张"),
	jian("件"),
	xiang("箱"),
	tiao("条"),
	bao("包"),
	he("盒");
	
	
	private  String label;
	private Unit(String label){
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	

}
