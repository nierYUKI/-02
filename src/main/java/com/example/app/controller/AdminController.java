package com.example.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.ShiftPreferences;
import com.example.app.domain.Shifts;
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
	public String postAdminShiftList(Model model, 
			ShiftPreferences shiftPreferences,
			HttpSession session,
			@RequestParam LocalDate selectDate) {
		model.addAttribute("selectDate", selectDate);
		// 1. 指定された日付でデータベースからシフトの希望リストを取得
		List<ShiftPreferences> UsersShiftPreferencesList = shiftPreferencesMapper.selectShiftByDate(selectDate);
		/*		System.out.println(UsersShiftPreferencesList);
		
				// 2. 同じ日付でデータベースからシフトの希望リストをもう一度取得
				List<ShiftPreferences> shiftPreferencesList = shiftPreferencesMapper.selectShiftByDate(selectDate);
		
				// 3. アイアンシフトの最適なリストを格納するリスト
				List<ShiftPreferences> shiftList = new ArrayList<>();
		
				//				System.out.println(shiftPreferencesByStartTime);
		
				// 4. 各時間帯ごとに希望シフトをグループ化し、各グループ内で各ランクのアルバイト数を数える
				Map<LocalTime, Map<Integer, Long>> shiftByTimeAndRank = shiftPreferencesList.stream()
						.collect(Collectors.groupingBy(ShiftPreferences::getStartTime, // 時間帯ごとにグループ化
								Collectors.groupingBy(ShiftPreferences::getRankId, Collectors.counting()))); // 各ランクのアルバイト数を数える
		
				// 5. 各時間帯ごとに処理を行う
				shiftByTimeAndRank.forEach((startTime, rankCountMap) -> {
					for (Map.Entry<Integer, Long> entry : rankCountMap.entrySet()) {
						// 6. アイアンシフトのみを抽出して重複を除外し、最も若いpreferenceIdを持つものを選択
						if (entry.getKey() == 1 && entry.getValue() > 1) {
							List<ShiftPreferences> ironShifts = shiftPreferencesList.stream()
									.filter(shift -> shift.getStartTime().equals(startTime) && shift.getRankId() == 1)
									.distinct() // 重複を除外
									.collect(Collectors.toList());
		
						// ironShiftsリストから、ShiftPreferencesオブジェクトのpreferenceIdを比較するためのComparatorを使用して、最小の要素を取得します。
							ShiftPreferences youngestIronShift = ironShifts.stream()
									.min(Comparator.comparing(ShiftPreferences::getPreferenceId))
								
									// orElse(null)は、リストが空の場合にデフォルト値としてnullを返します。
									.orElse(null);
							
							// 7. 最も若いpreferenceIdを持つアイアンシフトを選択し、リストに追加
							if (youngestIronShift != null) {
								shiftList.add(youngestIronShift);
								
							// 1. 加工したデータのリスト（shiftList）を元のデータに組み込む
								for (ShiftPreferences ironShift : shiftList) {
								    // もし元のデータに加工したデータのpreferenceIdと同じものがなければ、追加する
								    if (!UsersShiftPreferencesList.contains(ironShift)) {
								        UsersShiftPreferencesList.add(ironShift);
								    }
								}
		
								// 2. 元のデータから、加工したデータの要素と同じ条件を満たす要素を削除する
								UsersShiftPreferencesList.removeIf(shift -> shiftList.contains(shift));
		
							}
						}
					}
				});*/

		// 8. 最終的に選択されたアイアンシフトのリストをコンソールに出力
//				System.out.println("う" + UsersShiftPreferencesList);

		// 9. モデルに希望シフトリストを追加して、ビューに渡す
		model.addAttribute("ShiftPreferences", UsersShiftPreferencesList);
		//session.setAttribute("UsersShiftPreferencesList", UsersShiftPreferencesList);

		// 10. adminHomeページに戻る
		//今悩んでいる所
		//return "redirect:/admin/addShift?selectDate=" + selectDate;
		return "adminHome";
	}

	//確定シフトへ日付毎の希望シフトを登録
	@GetMapping("/addShift")
	public String addShift(Model model,HttpSession session, @RequestParam LocalDate selectDate,Shifts shifts) {
		//session.getAttribute("UsersShiftPreferencesList");
		//shiftService.getShiftByDate(selectDate);
		
		return "Shift";
	}

	
	@PostMapping("/addShift")
	public String postShift(Model model,@RequestParam LocalDate selectDate,Shifts shifts) {
		shiftService.getShiftByDate(selectDate);
		
		return "redirect:/admin/ShiftList?selectDate=" + selectDate;
		
	}
	//日毎の確定シフト一覧ページ表示処理
	@GetMapping("/ShiftList")
	public String getShiftList(Model model,@RequestParam LocalDate selectDate,Shifts shifts) {
		shiftService.getselectShiftAll(selectDate);
		//model.addAttribute("selectDate"+shifts);
		model.addAttribute("selectShifts",shiftService.getselectShiftAll(selectDate));
		/*System.out.println(shiftService.getselectShiftAll(selectDate));*/
		return "ShiftList";
	}
	
	@PostMapping("/ShiftList")
	public String postShiftList(Model model,@RequestParam LocalDate selectDate,Shifts shifts) {
		
		shiftService.getselectShiftAll(selectDate);
		model.addAttribute("selectShifts",shiftService.getselectShiftAll(selectDate));
		return "ShiftList";
	}
}