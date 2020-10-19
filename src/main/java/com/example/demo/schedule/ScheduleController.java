package com.example.demo.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ScheduleController {
	
	@GetMapping(value ="/schedule")
	@ResponseBody
	public MyLectureSchedule MySchedule() throws Exception {
		
		Document doc = Jsoup.connect("http://bctl.bu.ac.kr/User.do")
				.data("userId", "20153283")
				.data("password", "dbstn1357!@")
				.data("userDTO.loginType","30")
				.data("cmd","loginUser")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Referer", "http://bctl.bu.ac.kr/Main.do?cmd=viewHome")
				.header("Origin", "http://bctl.bu.ac.kr")
				.header("Host", "bctl.bu.ac.kr")
				// and other hidden fields which are being passed in post request.
				.userAgent("Mozilla")
				.post();
		
		Connection.Response response = Jsoup.connect("http://bctl.bu.ac.kr/User.do")
				.data("userId", "20153283")
				.data("password", "dbstn1357!@")
				.data("userDTO.loginType","30")
				.data("cmd","loginUser")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Referer", "http://bctl.bu.ac.kr/Main.do?cmd=viewHome")
				.header("Origin", "http://bctl.bu.ac.kr")
				.header("Host", "bctl.bu.ac.kr")
				// and other hidden fields which are being passed in post request.
				.userAgent("Mozilla")
				.method(Connection.Method.POST)
				.execute();
		
		Map<String,String> cookies = response.cookies();
		String cookie = "";
		
		for(Entry<String, String> c : cookies.entrySet()) {
			cookie = cookie + c.getKey() + "=" + c.getValue()+";";
		}
		
		String user = doc.getElementsByClass("login").get(0).getElementsByClass("mt5").get(0).text().trim();
		
		Element courseBox = doc.getElementById("courseBox");
		Elements myClasses = courseBox.select("option");
		Map<String,String> myClassesMap = new HashMap<String,String>();
		for (Element e : myClasses) {
			if(e.attr("value") != "") {
				String className = e.text() + "-" + e.attr("value").split(",")[1].trim();
				String href = viewCourse(e.attr("value")).trim(); 
				myClassesMap.put(className, href);
			}
		}
		MyLectureSchedule mySchedule = new MyLectureSchedule();
		Lecture myLectures = new Lecture();
		for(Element e : myClasses) {
			if(e.attr("value").equals("")) {
				continue;
			}
			String classKey = e.attr("value").split(",")[0];
			String prName = e.attr("value").split(",")[1];
			String cn = e.text().trim();
			HashMap<String,String> urlList = new HashMap<String,String>();
			urlList.put("학습목차", "http://bctl.bu.ac.kr/Course.do?cmd=viewStudyHome&courseDTO.courseId="+classKey+"&boardInfoDTO.boardInfoGubun=study_home&boardGubun=study_course&gubun=study_main");
			urlList.put("과제", "http://bctl.bu.ac.kr/Report.do?cmd=viewReportInfoPageList&boardInfoDTO.boardInfoGubun=report&courseDTO.courseId="+classKey+"&mainDTO.parentMenuId=menu_00104&mainDTO.menuId=menu_00063");
			
			HashMap<String,Object> lectureMap = new HashMap<String,Object>();
			for(int z = 0; z<urlList.size(); z++) {
				
				if(z==0) {
					String curpage = "";
					Document work = Jsoup.connect(urlList.get("과제")+curpage)
							.header("Content-Type", "application/x-www-form-urlencoded")
							.header("Cookie", cookie)
							.get();
					HashMap<String,Work> workMap = new HashMap<String,Work>();
					Element listBox = work.getElementById("listBox");
					
					String noticeHref = work.getElementById("1").getElementsByClass("menuSub mp2").select("li").get(0).select("a").attr("href");
					urlList.put("공지사항", "http://bctl.bu.ac.kr"+noticeHref);
					
					if(listBox.selectFirst("div").className().equals("search_unfine")) {
						lectureMap.put("과제", workMap);
						continue;
					}else {
						Elements list = listBox.getElementsByClass("listContent pb20");
						int i = 0;
						for(Element div : list) {
							
								
							Elements tables = div.select("table");
								
							for(Element table : tables) {
								Work w = new Work();
								w.setTitle(div.getElementsByClass("f14").text());
								Elements heads = table.select("thead").select("th");
								for(Element head : heads) {
									HashMap<String,String> map = new HashMap<String,String>();
									if(head.text().equals("제출기간")) {
										map.put(head.text(), table.select("tbody").select("td").get(0).text());
										w.setTime(map);;
									}
									if(head.text().equals("제출여부")) {
										map.put(head.text(), table.select("tbody").select("td").get(3).text());
										w.setIsPresent(map);
									}
								}
								w.setContent(div.getElementsByClass("cont pb0").text());
								workMap.put(String.valueOf(i), w);
								i++;
							}

						}
					}
					

					
					lectureMap.put("과제", workMap);
				}
				
				if(z == 1) {
					String curPage = "0";
					Boolean f = true;
					HashMap<String,HashMap<String,HashMap<String,String>>> lectureInfoMap = new HashMap<String,HashMap<String,HashMap<String,String>>>();
					
					while(f) {
						
						int i =0;	
						Document lectureIndex = Jsoup.connect(urlList.get("학습목차"))
								.header("Content-Type", "application/x-www-form-urlencoded")
								.header("Cookie", cookie)
								.data("lessonContentsDTO.pageGubun","next")
								.data("editGubun","B")
								.data("cmd","viewStudyHome")
								.data("lessonContentsDTO.endNow",curPage)
								.post();
						
						
						
						Element table = lectureIndex.getElementsByClass("boardListBasic2").get(0);
						Elements titles = table.select("thead").select("th");
					

						Elements infos = table.select("tbody").select("td");
						
						for(Element title : titles) {
							String t = title.text();
							if(t.equals("")) continue;
							
							Elements divs = infos.get(i).select("div");
							HashMap <String,HashMap<String,String>> infoMap = new HashMap<String,HashMap<String,String>>();
							int v=1;
							int c=1;
							for(Element div : divs) {
								
								if(div.hasClass("video on")) {
									String l_title = div.selectFirst("li").text();
									String state = div.getElementsByClass("bar").first().selectFirst("li").text();
									HashMap<String,String> map = new HashMap<String,String>();
									map.put("title", l_title);
									map.put("state", state);
									infoMap.put("video-"+String.valueOf(v), map);
									v++;
								}else {
									String l_title = div.selectFirst("li").text();
									String state = div.getElementsByClass("bar").first().selectFirst("li").text();
									HashMap<String,String> map = new HashMap<String,String>();
									map.put("title", l_title);
									map.put("state", state);
									infoMap.put("check-"+String.valueOf(c), map);
									c++;
								}
							}
							
							lectureInfoMap.put(t, infoMap);
							i++;
							
						}
						
						if(!lectureIndex.getElementsByClass("arrowRight").isEmpty()) {
							curPage = String.valueOf((Integer.parseInt(curPage) + 3)); 
						}else {
							f= false;
						}
						
					}
					
					lectureMap.put("학습목차", lectureInfoMap);
					
				}
				
				
				
				if(z==2) {
					
					String curpage = "";
					Boolean f = true;
					String totalPageNum;
					int i=1;
					int j =0;
					HashMap<String,Notice> noticeMap = new HashMap<String,Notice>();
					
					while(f) {
						
						Document notice = Jsoup.connect(urlList.get("공지사항")+curpage)
								.header("Content-Type", "application/x-www-form-urlencoded")
								.header("Cookie", cookie)
								.get();
						
						Element listBox = notice.getElementById("listBox");
						if(!listBox.getElementsByClass("search_unfine").isEmpty()) {
							break;
						}else {
							totalPageNum = listBox.getElementsByClass("paginator_pages").get(0).text().split(" ")[0];
							curpage = "&curPage="+String.valueOf(i);
							if(String.valueOf(i).equals(totalPageNum)) {
								f=false;
							}
							Elements listContents = listBox.getElementsByClass("listContent");
							for(Element listContent : listContents) {
								Notice n = new Notice();
								String title = listContent.getElementsByClass("boardTitleNcontent TITLE_ORIGIN").get(0).text();
								Elements items = listContent.getElementsByClass("info").get(0).getElementsByClass("fr mr10").select("li");
								HashMap<String,String> map = new HashMap<String,String>();
								for(Element li : items) {
									map.put(li.text().split(":")[0].trim(), li.text().split(":")[1].trim());
								}
								String content = listContent.getElementsByClass("cont boardTitleNcontent BOARD_CONTENTS_ORIGIN").get(0).text();
								
								n.setTitle(title);
								n.setInfo(map);
								n.setContent(content);
								noticeMap.put(String.valueOf(j), n);
								j++;
							}

							
							i++;
							
						}
						
					}
					
					
					lectureMap.put("공지사항", noticeMap);
				}
				
			}
			
			myLectures.setLecture(cn,lectureMap);
			
		}
		
		mySchedule.setSchedule(user,myLectures);
		return mySchedule;
		
	}

	public String viewCourse(String courseIdNownerName){

		String courseId = courseIdNownerName.split(",")[0];

		String courseOwnerName = courseIdNownerName.split(",")[1].trim();
		
		String openType = courseIdNownerName.split(",")[2].trim();
		
		String href = "";
		if(courseId!="" && courseOwnerName != ""){
			if(!openType.equals("open")&&!openType.equals("mooc")){
				href="http://bctl.bu.ac.kr/Course.do?"+
						 "cmd=viewStudyHome"+
						 "&courseDTO.courseId="+courseId+
						 "&boardInfoDTO.boardInfoGubun=study_home&boardGubun=study_course&gubun=study_main";
			}else{
				href="http://bctl.bu.ac.kr/Lesson.do?"+
						 "cmd=viewLessonContentsList"+
						 "&courseDTO.courseId="+courseId+
						 "&pageGubun=study_home"+
						 "&type=U"+
						 "&boardInfoDTO.boardInfoGubun=openkyoan&gubun=study_course&pageGubun=study_home";
			}
			
		}
		
		return href;
		
	}


}
