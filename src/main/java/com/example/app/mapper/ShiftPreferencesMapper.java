package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.ShiftPreferences;
//playerテーブルに対してのメソッド定義
//以前のDAOのような存在
@Mapper
public interface ShiftPreferencesMapper {
	
	//希望シフト登録
	void ShiftPreferencesAdd(ShiftPreferences shiftPreferences);
	
	//希望シフトの一覧
	List<ShiftPreferences> selectShiftPreferencesAll();
	
	//ユーザー希望シフトの個別管理
	List<ShiftPreferences> shiftPreferencesById(int id);

}
