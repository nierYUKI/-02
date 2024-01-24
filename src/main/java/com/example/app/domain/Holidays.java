package com.example.app.domain;

import java.time.LocalDate;

import lombok.Data;

@Data
//休日のテーブル
public class Holidays {
	
	private Integer holidayId;//休日のID(主キー)
	private Integer userId;//ユーザーID (外部キー、ユーザーテーブルと関連付け)
	private LocalDate holidayDate;//休日(休日の日付)

}
