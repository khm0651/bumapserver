package com.example.demo.bus;

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
public class BusController {

	private static String BU_BUS_URL = "http://www.bu.ac.kr/web/3485/subview.do";
	private static String BU_BASE_URL = "http://www.bu.ac.kr";
	private static ArrayList<Bus> busList = new ArrayList<Bus>();


	@GetMapping(value ="/bus")
	@ResponseBody
	public ArrayList<Bus> busRest() throws IOException {
		
		Document doc = Jsoup.connect(BU_BUS_URL).get();
		Elements bus_wrap = doc.getElementsByClass("bus_wrap");
		for(Element b : bus_wrap) {
			String name = b.select("h3").text();
			ArrayList<String> imgList = new ArrayList<String>();
			String price = "";
			Elements imgs = b.select("img");

			ArrayList<String> toBuDay = new ArrayList<String>();
			Boolean isToBu = false;
			ArrayList<String> toBuTime = new ArrayList<String>();
			ArrayList<String> toHomeDay = new ArrayList<String>();
			Boolean isToHome = false;
			ArrayList<String> toHomeTime = new ArrayList<String>();
			String toHomeTakePlace = "";
			String toBuTakePlace = "";
			for(Element img : imgs) {
				imgList.add(BU_BASE_URL + img.attr("src"));
			}
			Elements trs = b.select("tbody").select("tr");
			for(int i =0; i<trs.size(); i++) {
				Elements tds = trs.get(i).select("td");
				for(int j = 0; j<tds.size(); j++) {
					if(i==0 && j==0) {
						price = tds.get(j).text().split(":")[1];

					}
					if(i==0 && j==1) {
						if(tds.get(j).text().equals("등교")) {
							isToBu = true;
						}
					}
					if(isToBu && i==1 &&j==0) {
						if(tds.get(j).text().equals("하교")) {
							isToBu=false;
						}
					}
					if(isToBu) {
						if(i==0 && j==2) {
							toBuDay.add(tds.get(j).text());
						}
						if(i==0 && j==3) {
							toBuTime.add(tds.get(j).text());
						}
						if(i==0 && j==4) {
							toBuTakePlace = tds.get(j).text();
						}
						if(isToBu && i==1 && j==0) {
							toBuDay.add(tds.get(j).text());
						}
						
						if(isToBu && i==1 && j==1) {
							toBuTime.add(tds.get(j).text());
						}
						
						if( i==2 && j==1) {
							toHomeDay.add(tds.get(j).text());
						}
						if( i==2 && j==2) {
							toHomeTime.add(tds.get(j).text());
						}
						if( i==2 && j==3) {
							toHomeTakePlace = tds.get(j).text();
						}
						if( i==3 && j==0) {
							toHomeDay.add(tds.get(j).text());
						}
						if( i==3 && j==1) {
							toHomeTime.add(tds.get(j).text());
						}
						
					}else {

					
						if( i==1 && j==1) {
							toHomeDay.add(tds.get(j).text());
						}
						if( i==1 && j==2) {
							toHomeTime.add(tds.get(j).text());
						}
						if( i==1 && j==3) {
							toHomeTakePlace = tds.get(j).text();
						}
						if( i==2 && j==0) {
							toHomeDay.add(tds.get(j).text());
						}
						if( i==2 && j==1) {
							toHomeTime.add(tds.get(j).text());
						}
					}
					
					
					
				}
			}
			System.out.println("1");
			Bus bus = new Bus();
			bus.setBusStation(name);
			bus.setImg(imgList);
			bus.setPirce(price);
			HashMap<String, String> map1 = new HashMap<String,String>();
			for(int i =0 ; i<toBuDay.size(); i++) {
				map1.put(toBuDay.get(i), toBuTime.get(i));
			}
			bus.setToBu(map1);
			System.out.println("2");
			bus.setToBuTakePlace(toBuTakePlace);
			HashMap<String, String> map2 = new HashMap<String,String>();
			for(int i =0 ; i<toHomeDay.size(); i++) {
				if(toHomeTime.get(0).contains("운행없음")) {
					toHomeTime.add(toHomeTime.get(0));
				}
				map2.put(toHomeDay.get(i), toHomeTime.get(i));
			}
			bus.setToHome(map2);
			bus.setToHomeTakePlace(toHomeTakePlace);
			busList.add(bus);
		}
		
		
		ArrayList<Bus> result = (ArrayList<Bus>) busList.clone();
		busList.clear();
		return result;
		
	}



	
	
}
