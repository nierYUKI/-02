package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.ShiftCreators;
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
	  //通常のユーザー情報を挿入
		mapper.add(users);
		
		//シフト作成者情報を挿入
    ShiftCreators shiftCreators = new ShiftCreators();
    shiftCreators.setUserId(users.getUserId()); // ユーザーIDをセット
    shiftCreators.setJobRoleId(users.getRoleId()); // jobRoleIdをセット

    // シフト作成者情報を挿入するメソッドを呼び出す
    mapper.addShiftCreators(shiftCreators);
		return "redirect:/user/add";
	}
	
}
