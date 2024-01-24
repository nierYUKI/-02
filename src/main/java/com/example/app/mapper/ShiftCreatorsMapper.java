package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.ShiftCreators;

@Mapper
public interface ShiftCreatorsMapper {

	void add(ShiftCreators shiftCreators);
	
}
