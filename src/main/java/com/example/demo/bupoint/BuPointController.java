package com.example.demo.bupoint;


import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuPointController {
	

	@GetMapping(value ="/bupoint")
	@ResponseBody
	public BuPoint buPoint() throws Exception {

		
		Document doc = Jsoup.connect("https://best.bu.ac.kr/main/loginPro.aspx")
				.data("rUserid", "20151096")
				.data("rPW", "chan3872!")
				.data("pro","1")
				.header("Content-Type", "application/x-www-form-urlencoded")
				// and other hidden fields which are being passed in post request.
				.userAgent("Mozilla")
				.post();
		
		Elements script = doc.select("script");
		String str = script.get(1).childNode(0).toString().replace("\r\n","");
		str = str.replace(" ","").substring(0);
		String[] arr = str.split("\n");
		String cookie = "";
		
		BuPoint myPoint = new BuPoint();
		for(String s : arr) {
			if(s.startsWith("document.cookie")) {
				cookie += s.substring("document.cookie=".length(),s.length());
			}
		}
		cookie = cookie.replace("\"", "");

		doc = Jsoup.connect("https://best.bu.ac.kr/Career/CareerDevelop/MileageTask.aspx?cTab=2&para=2")
				.header("Cookie", cookie)
				.get();
		Elements panel = doc.getElementsByClass("panel-primary wrapper");
		String name = panel.get(0).select("header").text();
		name = name.substring(0,name.indexOf("님"));
		Elements buDiv = panel.get(0).getElementsByClass("col-lg-12 tab_sm no-padder").get(0).select("div");
		ArrayList<String> buInfo = new ArrayList<String>();
		for(int i =1; i< buDiv.size() ; i++) {
			int idx = buDiv.get(i).text().lastIndexOf(" ");
			char[] carr = buDiv.get(i).text().toCharArray();
			carr[idx] = '-';
			String s = String.valueOf(carr);
			
			buInfo.add(s);
		}
		
		myPoint.setName(name);
		myPoint.setBuinfo(buInfo);
		return myPoint;
	
	}

}
