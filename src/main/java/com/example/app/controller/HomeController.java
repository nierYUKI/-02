package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.mapper.JobRankMapper;
import com.example.app.mapper.JobRoleMapper;
import com.example.app.mapper.UsersMapper;
import com.example.app.service.UsersService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/home")
public class HomeController {
	
	private final UsersMapper Usersmapper;
	private final JobRankMapper jobRankMapper;
	private final JobRoleMapper jobRoleMapper;
	private final UsersService usersService;
	
	
	@GetMapping("/shiftAdd")
	public String getShiftAdd(Model model,HttpSession session) {
		session.getAttribute("loggedInUser");
		System.out.println(session.getAttribute("loggedInUser")	);
		return "desiredShift";
		
	}
}
