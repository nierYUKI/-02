package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.Users;
import com.example.app.mapper.JobRankMapper;
import com.example.app.mapper.JobRoleMapper;
import com.example.app.mapper.UsersMapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/home")
public class HomeController {
	
	private final UsersMapper Usersmapper;
	private final JobRankMapper jobRankMapper;
	private final JobRoleMapper jobRoleMapper;

	
	
	@GetMapping("/shiftAdd")
	public String getShiftAdd(Model model,HttpSession session,Users users) {
		session.getAttribute("loggedInUser");
		//モデルでビューにセッション情報を注入していると思う。
		model.addAttribute(session.getAttribute("loggedInUser"));
		
		
		System.out.println(session.getAttribute("loggedInUser")	);
		return "desiredShift";
		
	}
}
