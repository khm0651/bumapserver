package com.example.demo.calender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalenderController {

	private static String KOREA_COVID_DATAS_URL = "http://www.bu.ac.kr/web/3443/subview.do";
	private static ArrayList<Calender> calenderList = new ArrayList<Calender>();
//	private HashMap<String,ArrayList<HashMap<String,ArrayList<HashMap<String,ArrayList<String>>>>>> map = new HashMap<String,HashMap<String,HashMap<String,ArrayList<String>>>>();
	
	@GetMapping(value = "/calendar")
	@ResponseBody
	public ArrayList<Calender> calenderRest() throws IOException {
		Document doc = Jsoup.connect(KOREA_COVID_DATAS_URL).get();
		Elements yearSchdulWrap = doc.getElementsByClass("yearSchdulWrap");
		
		//String year = doc.getElementsByClass("search").select("strong").text().replace(" ","");
		
		
		for(Element y : yearSchdulWrap) {
			String year = y.select("p").text().substring(0,4);
			String v = y.select("p").attr("id");
			int m = Integer.parseInt(v.substring(v.lastIndexOf("h")+1,v.length()));
			
			if(!(m >= 1 && m <= 12)) {
				year = String.valueOf((Integer.parseInt(year)+1));
			}
			Elements dateList = y.getElementsByClass("scheList").select("span");
			Elements contentList = y.getElementsByClass("scheList").select("strong");
			int i = 0;
			for(Element d : dateList) {
				
				Calender calender = new Calender();
				String date = d.text().replace(" ", "");
				int month = Integer.parseInt(date.substring(0,2));
				int day = Integer.parseInt(date.substring(3, 5));
				String content = contentList.get(i).text();
				
				if(date.contains("~")) {
					
					int lastday = Integer.parseInt(date.substring(12, 14));
					
					
					for(int startday = day; startday<=lastday; startday++) {
						calender.setYear(year);
						calender.setMonth(month);
						calender.setDay(startday);
						calender.setContent(content);
						calenderList.add(calender);
						calender = new Calender();
					}

				}else {
					calender.setYear(year);
					calender.setMonth(month);
					calender.setDay(day);
					calender.setContent(content);
					calenderList.add(calender);
				}
				
				i++;
			}
		}
		
		
		ArrayList<Calender> result = (ArrayList<Calender>) calenderList.clone();
		calenderList.clear();
		

		
		return result;
	}
	
	
}
