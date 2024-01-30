package com.example.app.service;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersService {

	boolean selectLogin(String userName,String password);
}
