package com.example.demo.schedule;

import java.util.HashMap;

public class MyLectureSchedule {
	
	private HashMap<String,Lecture> schedule = new HashMap<String,Lecture>();

	public HashMap<String,Lecture> getSchedule() {
		return schedule;
	}

	public void setSchedule(String k,Lecture v) {
		this.schedule.put(k, v);
	}

}
