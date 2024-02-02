package com.example.app.controller;

import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.ShiftPreferences;
import com.example.app.domain.Users;
import com.example.app.mapper.JobRankMapper;
import com.example.app.mapper.JobRoleMapper;
import com.example.app.mapper.ShiftPreferencesMapper;
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
	private final ShiftPreferencesMapper shiftPreferencesMapper;
	
	
	@GetMapping("/shiftAdd")//希望シフト登録の準備
	public String getShiftAdd(Model model,HttpSession session,Users users) {
		session.getAttribute("loggedInUser");
		model.addAttribute("shiftPreferences",new ShiftPreferences());
//		System.out.println(session.getAttribute("loggedInUser")	);
		return "desiredShift";
		
	}
	
	@PostMapping("/shiftAdd")//希望シフト登録のフォームからの取得
	public String postShiftAdd(
			Model model,
			HttpSession session,
			@RequestParam LocalTime startTime,
			ShiftPreferences shiftPreferences
			) {
		Users users = (Users) session.getAttribute("loggedInUser");
		shiftPreferences.setUserId(users.getUserId());
		System.out.println("shiftPreferences->"+shiftPreferences);
		shiftPreferencesMapper.ShiftPreferencesAdd(shiftPreferences);
		System.out.println("LocalTime->"+startTime);
		
		return "desiredShift";
	}
	
	
}
