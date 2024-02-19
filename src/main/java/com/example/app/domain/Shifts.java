package com.example.app.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
//シフトテーブル
public class Shifts {
	
	private Integer shiftId;//主キー
	private Integer userId;//外部キー、ユーザーテーブルと関連付け
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime startDateTime;//シフトの開始時間
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime endDateTime;//シフトの終了時間
	private Integer isApproved;//シフトが承認されたかどうかのフラグ
	private Integer shiftType;//早番、遅番、夜勤などのタイプ分け
	private Integer breakDuration;//シフトの休憩時間
	
	//2月8日に追記。シフト日を入れるの忘れていた；；
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dayOfWeek;//確定シフト日
	
	private String userName;//アルバイトの名前
	private String rankName;//アルバイトのランク名
	private Integer rankId;//ランクID

}
