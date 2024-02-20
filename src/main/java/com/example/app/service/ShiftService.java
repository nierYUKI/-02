package com.example.app.service;

import java.time.LocalDate;
import java.util.List;

import com.example.app.domain.Shifts;

public interface ShiftService {
	
	//アルバイトのランクの比較をする為に必要な情報
  //希望シフトのアルバイトの処理
	//確定テーブルに希望シフトの登録
	void getShiftByDate(LocalDate selectDate);
	
	
	//日付毎の確定シフト一覧取得
	List<Shifts> getselectShiftAll(LocalDate selectDate);
	
//日程の範囲指定した希望シフトの確定テーブルの登録処理
	void getweekShiftDate(LocalDate startDate,LocalDate endDate);

	
	

}
