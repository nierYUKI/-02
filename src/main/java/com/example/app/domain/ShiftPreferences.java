package com.example.app.domain;

import java.time.LocalDate;

import lombok.Data;

@Data
//希望シフトテーブル
public class ShiftPreferences {
	private Integer preferenceId;//主キー
	private Integer userId;//外部キー、ユーザーテーブルと関連付け
	private LocalDate dayOfWeek;//希望シフトの曜日
	private LocalDate startTime;//希望シフトの開始時間
	private LocalDate endTime;//希望シフトの終了時間
	private Integer isApproved;//希望シフトが承認されたかのフラグ

}
