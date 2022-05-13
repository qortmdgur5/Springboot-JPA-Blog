package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.service.BoardService;



@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	
	@GetMapping({"", "/"})
	public String index(Model model) {		// 컨트롤러에서 세션을 어떻게 찾는지?
		model.addAttribute("boards", boardService.글목록());
		return "index";
	}
	
	// USER 권한도 같이 가져와야함, 작성자 정보를 알아야 하니까
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}

}
