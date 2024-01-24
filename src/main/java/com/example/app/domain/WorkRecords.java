package com.example.app.domain;

import java.time.LocalDate;

import lombok.Data;

@Data
//アルバイトの出勤退勤実績テーブル
public class WorkRecords {
	private Integer recodeId;//実績ID
	private Integer userId;//外部キー、ユーザーテーブルと関連付け
	private LocalDate startDateTime;//アルバイトの出勤時間
	private LocalDate endDateTime;//アルバイトの退勤時間
	private Integer breakDuration;//アルバイトの休憩時間
	private Integer hourlyRate;//アルバイトの時給
}
