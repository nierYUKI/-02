package com.example.app.domain;

import lombok.Data;

@Data
//アルバイトのランク(時給テーブル)
public class JobRank {
	private Integer rankId;//時給のID
	private String rankName;//ランクの名前
	private Integer rankHourlyRate;//ランクの時給
}
