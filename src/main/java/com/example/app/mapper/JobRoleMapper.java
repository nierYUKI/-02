package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.JobRole;
@Mapper
public interface JobRoleMapper {
	
	//アルバイトかシフト作成者か店長の一覧情報
	List<JobRole>selectJobRoleAll();

}
