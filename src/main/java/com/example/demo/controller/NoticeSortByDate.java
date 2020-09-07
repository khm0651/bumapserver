package com.example.demo.controller;

import java.util.Comparator;

public class NoticeSortByDate implements Comparator<Notice>{

	@Override
	public int compare(Notice o1, Notice o2) {
		return o2.getArtclDate().compareTo(o1.getArtclDate());
	}

	
}
