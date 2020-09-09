package com.example.demo.privacy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class privacyController {

	@GetMapping("/privacy")
	public String privacypage() {
		return "privacy.html";
	}
}
