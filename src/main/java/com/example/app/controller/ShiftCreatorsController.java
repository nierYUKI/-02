package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.ShiftCreators;
import com.example.app.mapper.ShiftCreatorsMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ShiftCreatorsController {

	private final ShiftCreatorsMapper mapper;

	@GetMapping("/add/ShiftCreators") //シフト作成者登録
	public String getadd(Model model) {
		model.addAttribute("shiftCreators", new ShiftCreators());
		return "save";
	}

	@PostMapping("/add/ShiftCreators") //作成者登録
	public String postadd(Model model, ShiftCreators shiftCreators) {
		mapper.add(shiftCreators);
		return "redirect:/save";

	}
}