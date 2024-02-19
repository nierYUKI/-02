package com.example.app.service;

import java.time.LocalDate;
import java.util.List;

import com.example.app.domain.Shifts;

public interface ShiftService {
	
	//アルバイトのランクの比較をする為に必要な情報
  //希望シフトのアルバイトの処理
	void getShiftByDate(LocalDate selectDate);
	
	
	//日付毎の確定シフト
	List<Shifts> getselectShiftAll(LocalDate selectDate);

}
