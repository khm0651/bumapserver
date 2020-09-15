package com.example.demo.schedule;

import java.util.HashMap;

public class Work {
	
	private String title;
	private HashMap<String,String> time;
	private HashMap<String,String> isPresent;
	private String content;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public HashMap<String,String> getTime() {
		return time;
	}
	public void setTime(HashMap<String,String> time) {
		this.time = time;
	}
	public HashMap<String,String> getIsPresent() {
		return isPresent;
	}
	public void setIsPresent(HashMap<String,String> isPresent) {
		this.isPresent = isPresent;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
