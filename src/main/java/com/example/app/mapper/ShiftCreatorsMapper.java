package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.ShiftCreators;

@Mapper
public interface ShiftCreatorsMapper {

	//シフト作成者登録
	void add(ShiftCreators shiftCreators);
	
}
