package cn.edu.sdut.softlab.controller;

public enum ItemStatus {
	AVALIABLE("可借"),
	NOT_AVALIABLE("不可借");
	
	private String label;

	public String getLabel() {
		return label;
	}

	private  ItemStatus(String label) {
		this.label = label;
	}
	
}
