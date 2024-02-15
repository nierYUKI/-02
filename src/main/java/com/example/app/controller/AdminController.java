package com.example.app.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.ShiftPreferences;
import com.example.app.domain.Users;
import com.example.app.mapper.JobRankMapper;
import com.example.app.mapper.JobRoleMapper;
import com.example.app.mapper.ShiftPreferencesMapper;
import com.example.app.mapper.UsersMapper;
import com.example.app.service.ShiftService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UsersMapper Usersmapper;
	private final JobRankMapper jobRankMapper;
	private final JobRoleMapper jobRoleMapper;
	private final ShiftPreferencesMapper shiftPreferencesMapper;
	private final ShiftService shiftService;

	@GetMapping("/adminHome") //アルバイトの希望シフト一覧
	public String getAdminShiftList(Model model, HttpSession session, Users users) {
		session.getAttribute("loggedInUser");
		model.addAttribute("shiftPreferences", new ShiftPreferences());
		return "adminHome";
	}

	@PostMapping("/adminHome") //アルバイトの希望シフト一覧
	public String postAdminShiftList(Model model, ShiftPreferences shiftPreferences,
			@RequestParam LocalDate selectDate) {

		List<ShiftPreferences> UsersShiftPreferencesList = shiftPreferencesMapper.selectShiftByDate(selectDate);
		/*System.out.println(UsersShiftPreferencesList);*/
		List<ShiftPreferences> shiftPreferencesList = shiftPreferencesMapper.selectShiftByDate(selectDate);
    List<ShiftPreferences> shiftList = new ArrayList<>();

//				System.out.println(shiftPreferencesByStartTime);
		
		// Step 2: 各時間帯ごとに希望シフトをグループ化し、各グループ内で各ランクのアルバイト数を数える
		Map<LocalTime, Map<Integer, Long>> shiftByTimeAndRank = shiftPreferencesList.stream()
		        .collect(Collectors.groupingBy(ShiftPreferences::getStartTime, // 時間帯ごとにグループ化
		                 Collectors.groupingBy(ShiftPreferences::getRankId, Collectors.counting()))); // 各ランクのアルバイト数を数える
		/*		System.out.println(shiftByTimeAndRank);*/
    // Step 3: 各時間帯ごとに処理を行う
		shiftByTimeAndRank.forEach((startTime, rankCountMap) -> {
//			System.out.println("startTime->"+startTime);
//			for(Map.Entry<Integer, Long> entry: rankCountMap.entrySet()) {
//								System.out.println("entry.getKey()->"+entry.getKey());
//								System.out.println("entry.getValue()->"+entry.getValue());
//			
		    // ここで adjustShiftsInGroup メソッドを呼び出す
		    List<ShiftPreferences> ironShifts = shiftPreferencesList.stream()
		            .filter(shift -> shift.getStartTime().equals(startTime)&& shift.getRankId() == shiftPreferencesList.get(0).getRankId())
		            .distinct() // 重複を除外
		            .collect(Collectors.toList());
				System.out.println("あ"+ironShifts);
		    if (ironShifts.size() > 1) {
		        ShiftPreferences youngestIronShift = ironShifts.stream()
		                .min(Comparator.comparing(ShiftPreferences::getPreferenceId))
		                .orElse(null);
//		        System.out.println("い"+youngestIronShift);
					            if (youngestIronShift != null) {
						/*					      shiftPreferencesMapper.updateShiftPreference(youngestIronShift);*/
			                	shiftList.add(youngestIronShift);

		
					  }
					/*  		System.out.println("う"+shiftList);*/
		    //}
		    }});


		model.addAttribute("ShiftPreferences", UsersShiftPreferencesList);
		return "adminHome";
	}

@GetMapping("/addShift")
public String addShift(Model model,	@RequestParam  LocalDate selectDate
		) {
	shiftService.getShiftByDate(selectDate);
	return "Shift";
}
}