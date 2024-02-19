package com.example.app.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.ShiftPreferences;
import com.example.app.domain.Shifts;

@Mapper
public interface ShiftMapper {
	
	//確定シフト登録
	void ShiftsAdd(ShiftPreferences youngestIronShift);
	
	//確定シフトの一覧情報
	List<Shifts>selectShiftAll(LocalDate selectDate);
	
	

}
