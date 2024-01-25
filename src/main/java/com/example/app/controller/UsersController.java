package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.Users;
import com.example.app.mapper.UsersMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UsersController {
	
	private final UsersMapper mapper;

	@GetMapping("/add")//サービスユーザー登録
	public String getadd(Model model) {
		model.addAttribute("users",new Users());
		return "userAdd";
	}
	
	@PostMapping("/add")//サービスユーザー登録
	public String postadd(Model model, Users users) {
		System.out.println(users);
		mapper.add(users);
		return "redirect:/user/add";
	}
	
}
