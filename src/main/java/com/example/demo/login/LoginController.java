package com.example.demo.login;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.schedule.MyLectureSchedule;

@RestController
public class LoginController {

	@PostMapping(value ="/buLogin")
	@ResponseBody
	public Message BuLogin(@RequestBody LoginParam param) throws Exception {
		String id = param.getId();
		String pw = param.getPw();
		Message message = new Message();
		Document doc = Jsoup.connect("https://www.bu.ac.kr/subLogin/web/login.do")
				.data("userId", id)
				.data("userPwd", pw)
				.data("univerGu","1")
				.data("layout","DGcVFZrn28BwzRhhUMD3tA%3D%3D")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Referer", "http://bctl.bu.ac.kr/Main.do?cmd=viewHome")
				.header("Origin", "http://bctl.bu.ac.kr")
				.header("Host", "bctl.bu.ac.kr")
				// and other hidden fields which are being passed in post request.
				.userAgent("Mozilla")
				.post();
		if(doc.selectFirst("body").hasClass("_message")) {
			message.setMessage("status", "fail");
			String m = doc.selectFirst("body").text();
			message.setMessage("message", m);
		}else {
			message.setMessage("status", "success");
		}

		return message;
		
	}
}
