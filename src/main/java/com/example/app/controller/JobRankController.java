package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.mapper.JobRankMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/jobRank")
public class JobRankController {

	private final JobRankMapper jobRankMapper;
	
	@GetMapping
	public String jobRankList(Model model) {
		model.addAttribute("jobRanks",jobRankMapper.selectJobRankAll());
		return "JobRankList";
		
	}
	
}
