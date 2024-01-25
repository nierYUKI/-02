package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Users;

@Mapper
public interface UsersMapper {
	
	//ユーザー作成登録
	void add(Users users);

}
