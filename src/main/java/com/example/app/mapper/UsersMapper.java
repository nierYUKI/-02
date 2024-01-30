package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.ShiftCreators;
import com.example.app.domain.Users;

@Mapper
public interface UsersMapper {
	
	//ユーザー作成登録
	void add(Users users);
	
//roleIdにシフト作成者(shift_creatorsテーブル)の情報を
//同時に挿入する為のメソッド(1/25 11:40)
	void addShiftCreators(ShiftCreators shiftCreators);
	
	
	//
	Users selectByLoginId(String userName);
	
//	//ログイン時にユーザーの名前とパスワードで判別するメソッド
//	//1/30 9:50
//	Users selectByLogin(String userName,String password);

}
