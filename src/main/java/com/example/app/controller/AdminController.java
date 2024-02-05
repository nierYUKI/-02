package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/admin")
public class AdminController {
	
	private final UsersMapper Usersmapper;
	private final JobRankMapper jobRankMapper;
	private final JobRoleMapper jobRoleMapper;
	private final ShiftPreferencesMapper shiftPreferencesMapper;
	
	@GetMapping("/adminHome")//アルバイトの希望シフト一覧
	public String getAdminShiftList(Model model,HttpSession session,Users users) {
	session.getAttribute("loggedInUser");
	model.addAttribute("shiftPreferences",new ShiftPreferences());
	return "adminHome";
	
	
	
	}
}