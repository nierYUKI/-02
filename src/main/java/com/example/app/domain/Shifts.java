package com.example.app.domain;

import java.time.LocalDate;

import lombok.Data;

@Data
//シフトテーブル
public class Shifts {
	
	private Integer shiftId;//主キー
	private Integer userId;//外部キー、ユーザーテーブルと関連付け
	private LocalDate startDateTime;//シフトの開始時間
	private LocalDate endDateTime;//シフトの終了時間
	private Integer isApproved;//シフトが承認されたかどうかのフラグ
	private Integer shiftType;//早番、遅番、夜勤などのタイプ分け
	private Integer breakDuration;//シフトの休憩時間

}
