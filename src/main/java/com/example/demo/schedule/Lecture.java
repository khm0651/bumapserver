package com.example.demo.schedule;

import java.util.HashMap;

public class Lecture {

	private HashMap<String,HashMap<String,Object>> lecture = new HashMap<String,HashMap<String,Object>>();

	public HashMap<String,HashMap<String,Object>> getLecture() {
		return lecture;
	}

	public void setLecture(String k,HashMap<String,Object> v) {
		this.lecture.put(k, v);
	}
}
