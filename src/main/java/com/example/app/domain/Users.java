package com.example.app.domain;

import com.example.app.validation.AddGroup;
import com.example.app.validation.LoginGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
//アルバイトの情報
public class Users {
	private Integer userId;//主キー→他のテーブルと照合するキー
	@NotBlank(groups= {LoginGroup.class})
	@Size(min=1,max=60,groups= {AddGroup.class})
	private String userName;//アルバイトの名前
	@NotBlank(groups= {LoginGroup.class})
	@Size(min=6,max=80,groups= {AddGroup.class})
	private String password;//パスワード
	private Integer roleId;//アルバイトか管理者を判断するID
	private Integer rank;//アルバイトのランクを識別するID
//	private Integer hourlyRate;//ランク事の時給
	//1月26日金曜→アルバイトのランク(JobRankテーブル)に時給を設定しているので、不要となった
	
//roleIdにシフト作成者(shift_creatorsテーブル)の情報を
//同時に挿入する為の準備(1/25 11:40)
	private ShiftCreators shiftCreators;
}
