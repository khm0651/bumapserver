package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AllNoticeController {
	
	private static String BU_NOTICE_URL = "http://www.bu.ac.kr/web/3483/subview.do";
	private static String BU_BASE_URL = "http://www.bu.ac.kr";
	private static ArrayList<Notice> noticeList = new ArrayList<Notice>();


	@GetMapping(value ="/notice")
	@ResponseBody
	public ArrayList<Notice> noticeRest() {
		try {
			for(int i = 1; i<=6; i++) {
				Integer page = i;
				sendPostJson(page.toString());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ArrayList<Notice> result = (ArrayList<Notice>) noticeList.clone();
			result.sort(new NoticeSortByDate());
			noticeList.clear();
			return result;
			
		}
		
	}
	
	private static void sendPostJson(String page) throws Exception{
		URL url = new URL("http://www.bu.ac.kr/mainNotice/web/artclList.do");
		Map<String,Object> params = new LinkedHashMap(); // 파라미터 세팅
        params.put("layout", "K8T6lHgnWF8g6uSVl0w0CQ%3D%3D");
        params.put("page", page);
        params.put("srchColumn", "");
        params.put("srchWrd", "");
 
        StringBuilder postData = new StringBuilder();
        for(Map.Entry<String,Object> param : params.entrySet()) {
            if(postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
 
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes); // POST 호출
 
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
 
        String inputLine;
        String str="";
        Boolean isStart = false;
        Boolean isContinue = false;
        Notice notice = new Notice();
        while((inputLine = in.readLine()) != null) { // response 출력

     
        	
        	if(inputLine.contains("</tr>")) {
        		isStart=false;
        		if(notice.getArtclTitle() != null) {
        			noticeList.add(notice);
        		}
        		
        		notice = new Notice();
        	}
        	
        	if(isStart) {
        		inputLine = inputLine.replace("\t","");
        		if(inputLine.contains("</td>")) {
        			isContinue = false;
        			if(inputLine.contains("artclTdNum")) {
        				notice.setArtclnum( inputLine.substring(inputLine.indexOf(">")+1,inputLine.indexOf("</")));
        			}else if(inputLine.contains("artclTdRdate")) {
        				notice.setArtclDate(inputLine.substring(inputLine.indexOf(">")+1,inputLine.indexOf("</")));
        			}
        			str+=inputLine.toString();
        			System.out.println(str);
        			str="";
        		}else if(isContinue) {
        			if(inputLine.equals(""))continue;
        			if(inputLine.startsWith("<a")) {
        				String [] strArr = inputLine.split(" ");
        				if(strArr[1].contains("javascript")) {
        					notice.setArg1(inputLine.substring(inputLine.indexOf("(")+2,inputLine.indexOf("(")+5));

            				notice.setArg2 (inputLine.substring(inputLine.indexOf("(")+9,inputLine.indexOf(")")-1));
            				String hrefUrl = "/mainNotice/" + notice.getArg1()  + "/" + notice.getArg2() + "/artclView";
            				notice.setHref(kurl(hrefUrl));
        				}else {
            				String href = strArr[1].substring(6,strArr[1].length()-1);
            				notice.setHref(href);
        				}
        				
        			}else if(inputLine.startsWith("<span")) {
        				String title = inputLine.substring(inputLine.indexOf(">")+1,inputLine.indexOf("</"));
        				if(title.contains("&")) {
        					String tag = title.substring(title.indexOf("&"),title.indexOf(";")+1);
        					title = title.replace(tag, "'");
        					notice.setArtclTitle(title);
        				}else {
        					notice.setArtclTitle(title);
        				}
        				
        				
        			} 
        			
        			str+=inputLine.toString();
        		}else if(inputLine.contains("<td")) {
        			isContinue = true;
        			str+=inputLine.toString();
            	
        		}
        	}
        	
        	if(inputLine.contains("<tr")) {
        		isStart=true;
        		System.out.println("\n");
        	}
        	
           
        }
 
        in.close();
		

	}
	
	
	private static String kurl(String url) {
		return "http://www.bu.ac.kr"+url + ".do";
		
	}
}
