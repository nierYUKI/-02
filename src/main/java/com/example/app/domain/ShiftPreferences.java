package com.example.app.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
//希望シフトテーブル
public class ShiftPreferences {
	private Integer preferenceId;//主キー
	private Integer userId;//外部キー、ユーザーテーブルと関連付け
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dayOfWeek;//希望シフトの曜日
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime startTime;//希望シフトの開始時間
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime endTime;//希望シフトの終了時間
	private Integer isApproved;//希望シフトが承認されたかのフラグ

	//2月6日管理者のページにアルバイトの名前を表示する為に追記
	//ワークベンチではユーザーの名前を取得しているけど、JavaのDomain(箱)に
	//その記述が必要
	private String username;
	private String rankName;
}
