package com.example.demo.schedule;

import java.util.HashMap;

public class Notice {
	private String title;
	private String content;
	private HashMap<String,String> info;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public HashMap<String,String> getInfo() {
		return info;
	}
	public void setInfo(HashMap<String,String> info) {
		this.info = info;
	}
}
