package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.ShiftCreators;
import com.example.app.domain.Users;
import com.example.app.mapper.JobRankMapper;
import com.example.app.mapper.JobRoleMapper;
import com.example.app.mapper.UsersMapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UsersController {
	
	private final UsersMapper Usersmapper;
	private final JobRankMapper jobRankMapper;
	private final JobRoleMapper jobRoleMapper;

	//接続先URL(localhost:8080/user/add)
	//外部接続先URL(http://java.apps.rok.jp:22789/user/login)
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
		//System.out.println(users);
	  //通常のユーザー情報を挿入
		//System.out.println(jobRoleMapper.selectJobRoleAll());
		//System.out.println(jobRankMapper.selectJobRankAll());
		Usersmapper.add(users);
    model.addAttribute("jobRanks",jobRankMapper.selectJobRankAll());
    model.addAttribute("jobRoles",jobRoleMapper.selectJobRoleAll());
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
		return "userAddShow";

	}
	
	@GetMapping("/login")//ユーザーのログイン
	public String getlogin(Model model, Users users) {
		model.addAttribute("users",new Users());
		return "login";
	}
	
	@PostMapping("/login")//ログイン処理
  public String postLogin(@ModelAttribute Users users, Model model, HttpSession session) {
    // ログイン処理
		
    Users loggedInUser = Usersmapper.selectByLogin(users.getUserId());
    //確認用System.out.println(loggedInUser);
    
    //ユーザーIDが存在しているか。そのユーザーIDと登録されているパスワードが一致しているかのチェック
    if (loggedInUser != null && loggedInUser.getPassword().equals(users.getPassword())) {

      	//管理ユーザーログイン成功
      	if(loggedInUser.getRoleId() > 1) {
      		session.setAttribute("loggedInUser", loggedInUser);
      		return "redirect:/admin/adminHome";
      	}
      	
          // 通常ユーザーログイン成功
          session.setAttribute("loggedInUser", loggedInUser);
          return "redirect:/home/userHome"; // ログイン成功時
          		//リダイレクトする際にHomeControllerの「shiftAdd」へ移行可能
      } else {
          // パスワードが一致しない場合の処理(ログイン失敗)
          model.addAttribute("error", "Invalid username or password");
          return "redirect:/user/login"; // ログイン失敗時のページにリダイレクト


      }
	}
}


	