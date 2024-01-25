package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.JobRank;

//playerテーブルに対してのメソッド定義
//以前のDAOのような存在
@Mapper
public interface JobRankMapper {
	
	//アルバイトのランク一覧情報
	List<JobRank>selectJobRankAll();
	
	//アルバイトのランク個別情報
	JobRank selectJobRankById(int id);
	
	//アルバイトのランク追加
	void JobRankAdd(JobRank jobRank);
	
	//アルバイトのランク編集(更新)
	void JobRankEdit(JobRank jobRank);

}
