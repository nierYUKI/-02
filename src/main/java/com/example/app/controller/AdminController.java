package com.example.app.controller;

import java.time.LocalDate;
import java.util.List;

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
	
	@PostMapping("/adminHome")//アルバイトの希望シフト一覧
	public String postAdminShiftList(Model model,ShiftPreferences shiftPreferences,
			@RequestParam  LocalDate selectDate
			) {
		System.out.println(selectDate);
		List<ShiftPreferences> UsersShiftPreferencesList = shiftPreferencesMapper.selectShiftByDate(selectDate);
		System.out.println(UsersShiftPreferencesList);
		model.addAttribute("ShiftPreferences",UsersShiftPreferencesList);
		return "adminHome";
	}
	
	
}