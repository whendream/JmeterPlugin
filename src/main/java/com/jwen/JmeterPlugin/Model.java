package com.jwen.JmeterPlugin;

public class Model implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1108726816656448029L;

	
	private String name;
	private int age;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
