package com.example.app.service;

import java.time.LocalDate;

import com.example.app.domain.Shifts;

public interface ShiftService {
	
	//アルバイトのランクの比較をする為に必要な情報
  //希望シフトのアルバイトの処理
	void getShiftByDate(LocalDate selectDate);
	
	
	//上記2つのメソッドが終了すればshifts(シフト確定)テーブルに挿入するメソッド
	void addShifts(Shifts shifts);

}
