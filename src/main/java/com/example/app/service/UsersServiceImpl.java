package com.example.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Users;
import com.example.app.mapper.UsersMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

	private final UsersMapper mapper;
	
	@Override
	public boolean selectLogin(String userName, String password) {
	
		Users users = mapper.selectByLoginId(userName);
		if(users == null) {
		return false;
	}

		if(!password.equals(users.getPassword())) {
			return false;
		}
		return true;
}
}