package com.example.demo.login;

import java.util.HashMap;

public class Message {

	private HashMap<String,String> message = new HashMap<String,String>();

	public HashMap<String,String> getMessage() {
		return message;
	}

	public void setMessage(String k, String v) {
		message.put(k, v);
	}
	
	
}
