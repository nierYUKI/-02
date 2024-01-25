package com.example.app.domain;

import lombok.Data;

@Data
//アルバイトの情報
public class Users {
	private Integer userId;//主キー→他のテーブルと照合するキー
	private String userName;//アルバイトの名前
	private String password;//パスワード
	private Integer roleId;//アルバイトか管理者を判断するID
	private Integer rank;//アルバイトのランクを識別するID
	private Integer hourlyRate;//ランク事の時給
	
//roleIdにシフト作成者(shift_creatorsテーブル)の情報を
//同時に挿入する為の準備(1/25 11:40)
	private ShiftCreators shiftCreators;
}
