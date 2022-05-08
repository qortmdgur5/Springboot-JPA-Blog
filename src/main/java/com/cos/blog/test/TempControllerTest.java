package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
	
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("temphome");
		//파일리턴 기본경로 : src/main/resources/static
		//리턴명: /home.html
		//풀경로: src/main/resousrces/static/home.html
		return "/home.html";
	}

}
