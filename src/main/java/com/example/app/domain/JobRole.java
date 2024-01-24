package com.example.app.domain;

import lombok.Data;

@Data
//シフト作成者かアルバイトか判断するテーブル
public class JobRole {
	private Integer jobRoleId;
	private String jobRoleName;
}
