package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.ShiftCreators;
import com.example.app.domain.Users;
import com.example.app.mapper.JobRankMapper;
import com.example.app.mapper.JobRoleMapper;
import com.example.app.mapper.UsersMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UsersController {
	
	private final UsersMapper Usersmapper;
	private final JobRankMapper jobRankMapper;
	private final JobRoleMapper jobRoleMapper;

	//接続先URL(localhost:8080/user/add)
	@GetMapping("/add")//サービスユーザー登録
	public String getadd(Model model) {
		model.addAttribute("users",new Users());
		//アルバイトのランク情報一覧
		model.addAttribute("jobRanks",jobRankMapper.selectJobRankAll());
		//店の職種情報(アルバイト、シフト作成者、店長)
		model.addAttribute("jobRoles",jobRoleMapper.selectJobRoleAll());
		return "userAdd";
	}
	
	@PostMapping("/add")//サービスユーザー登録
	public String postadd(Model model, Users users) {
		System.out.println(users);
	  //通常のユーザー情報を挿入
		Usersmapper.add(users);
		//アルバイトかシフト作成者か管理者の以下の条件分岐
		//RoleIDで判定している
		if(users.getRoleId() >1) {
		//シフト作成者情報を挿入
    ShiftCreators shiftCreators = new ShiftCreators();
    shiftCreators.setUserId(users.getUserId()); // ユーザーIDをセット
    shiftCreators.setJobRoleId(users.getRoleId()); // jobRoleIdをセット

    // シフト作成者情報を挿入するメソッドを呼び出す
    Usersmapper.addShiftCreators(shiftCreators);	
		}
		return "redirect:/user/add";

	}
}
