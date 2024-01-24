package com.example.app.domain;

import lombok.Data;

@Data
//シフト作成者テーブル
public class ShiftCreators {
	
	private Integer shiftCreatorId;//主キー
	private Integer userId;//外部キー、ユーザーテーブルと関連付け
	private Integer jobRoleId;//アルバイト、シフト作成者の区別するID

	

}
