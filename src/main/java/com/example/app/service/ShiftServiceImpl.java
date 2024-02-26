package com.example.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.ShiftPreferences;
import com.example.app.domain.Shifts;
import com.example.app.mapper.JobRankMapper;
import com.example.app.mapper.ShiftMapper;
import com.example.app.mapper.ShiftPreferencesMapper;

@Service
public class ShiftServiceImpl implements ShiftService {

	private final ShiftPreferencesMapper shiftPreferencesMapper;
	private final JobRankMapper jobRankMapper;
	private final ShiftMapper shiftMapper;

	@Autowired
	public ShiftServiceImpl(ShiftPreferencesMapper shiftPreferencesMapper, JobRankMapper jobRankMapper,
			ShiftMapper shiftMapper) {
		this.shiftPreferencesMapper = shiftPreferencesMapper;
		this.jobRankMapper = jobRankMapper;
		this.shiftMapper = shiftMapper;
	}

	//List<ShiftPreferences>
	@Override
	public void getShiftByDate(LocalDate selectDate) {
		// 1. 指定された日付でデータベースからシフトの希望リストを取得
		List<ShiftPreferences> UsersShiftPreferencesList = shiftPreferencesMapper.selectShiftByDate(selectDate);

		// 2. アイアンシフトの最適なリストを格納するリスト
		List<ShiftPreferences> shiftList = new ArrayList<>();

		// 3. 各時間帯ごとに希望シフトをグループ化し、各グループ内で各ランクのアルバイト数を数える
		Map<LocalTime, Map<Integer, Long>> shiftByTimeAndRank = UsersShiftPreferencesList.stream()
				.collect(Collectors.groupingBy(ShiftPreferences::getStartTime, // 時間帯ごとにグループ化
						Collectors.groupingBy(ShiftPreferences::getRankId, Collectors.counting()))); // 各ランクのアルバイト数を数える
		//System.out.println("shiftByTimeAndRank"+shiftByTimeAndRank);

		// 4. 各時間帯の開始時間を取得
		for (Map.Entry<LocalTime, Map<Integer, Long>> entry : shiftByTimeAndRank.entrySet()) {
			LocalTime startTime = entry.getKey();
			Map<Integer, Long> rankCounts = entry.getValue();

		// 5. 各時間帯と各ランクのアルバイト数をログに出力
			System.out.println("開始時間: " + startTime);
			for (Map.Entry<Integer, Long> rankEntry : rankCounts.entrySet()) {
				int rankId = rankEntry.getKey();
				long count = rankEntry.getValue();
				System.out.println("  ランクID: " + rankId + ", アルバイト数: " + count);

		// 6. ランクIDが1の場合、最も若いpreferenceIdを持つアイアンシフトを選択し、リスト(shiftList)に追加
				if (rankId == 1) {
					List<ShiftPreferences> ironShifts = UsersShiftPreferencesList.stream()
							.filter(shift -> shift.getStartTime().equals(startTime) && shift.getRankId() == 1)
							.distinct() // 重複を除外
							.collect(Collectors.toList());
					System.out.println("ironShifts" + ironShifts);
					ShiftPreferences youngestIronShift = ironShifts.stream()
							.min(Comparator.comparing(ShiftPreferences::getPreferenceId))
							.orElse(null);
					System.out.println("youngestIronShift" + youngestIronShift);
					
					shiftList.add(youngestIronShift);
				} else {
				// ランクIDが1以外の場合、該当する希望シフトを抽出し、リストに追加
					List<ShiftPreferences> exit = UsersShiftPreferencesList.stream()
							.filter(shift -> shift.getStartTime().equals(startTime) && shift.getRankId() != 1)
							.distinct() // 重複を除外
							.collect(Collectors.toList());
					shiftList.addAll(exit);
				}

			}
			System.out.println("shiftList" + shiftList);
		}

	// 7. 選択された希望シフトをデータベースに登録
		for (ShiftPreferences shiftPreferences : shiftList) {
			//System.out.println(shiftPreferences);
			shiftMapper.ShiftsAdd(shiftPreferences);
		}
	}


	@Override
	//確定テーブルに登録した日付ごとのデータ一覧取得
	public List<Shifts> getselectShiftAll(LocalDate selectDate) {
		List<Shifts> selectShifts = shiftMapper.selectShiftAll(selectDate);
		//        System.out.println(selectShifts);
		return selectShifts;

	}

	@Override
	public void getweekShiftDate(LocalDate startDate, LocalDate endDate) {
	    int totalRegisteredShifts = 0; // 初期値を0に設定

	    // 1. 日付範囲でシフトの希望をデータベースから取得
	    List<ShiftPreferences> WeekUsersShiftPreferencesList = shiftPreferencesMapper.weekShiftDate(startDate, endDate);

	    // 2. 日付毎に希望シフトをグループ化して処理を開始するために日付をソート
	    TreeMap<LocalDate, List<ShiftPreferences>> sortedGroupedPreferencesByDate = WeekUsersShiftPreferencesList.stream()
	            .collect(Collectors.groupingBy(ShiftPreferences::getDayOfWeek, TreeMap::new, Collectors.toList()));

	    // 3. 各日付の希望シフトを処理
	    for (Map.Entry<LocalDate, List<ShiftPreferences>> entry : sortedGroupedPreferencesByDate.entrySet()) {
	        LocalDate date = entry.getKey();
	        List<ShiftPreferences> preferencesList = entry.getValue();

	        // 4. 各日付の希望シフトを開始時間でグループ化
	        Map<LocalTime, List<ShiftPreferences>> groupedPreferencesByStartTime = preferencesList.stream()
	                .collect(Collectors.groupingBy(ShiftPreferences::getStartTime));

	        int totalShifts = 0; // 日付ごとの総シフト数を初期化

	        // 5. ランダムに一つの希望シフトを選択する（省略）
	        Random random = new Random();

	        // 6. 各開始時間の希望シフトを処理
	        for (Map.Entry<LocalTime, List<ShiftPreferences>> startTimeEntry : groupedPreferencesByStartTime.entrySet()) {
	            LocalTime startTime = startTimeEntry.getKey();
	            List<ShiftPreferences> shiftPreferences = startTimeEntry.getValue();
	            System.out.println("Processing preferences for start time: " + startTime);

	            // 7. 各ランクのアルバイト数を数える
	            Map<Integer, Long> rankCounts = shiftPreferences.stream()
	                    .collect(Collectors.groupingBy(ShiftPreferences::getRankId, Collectors.counting()));
	            System.out.println("Rank counts for start time " + startTime + ": " + rankCounts);

	            // 8. ランクIDが1のアルバイトを1人だけ登録し、それ以外は最大3人まで登録
	            if (rankCounts.containsKey(1)) {
	                // ランクIDが1のアルバイトを登録する
	                totalShifts += (int) Math.min(rankCounts.getOrDefault(1, 0L), 1);
	            }

	            // 9. それ以外のランクのアルバイトを最大3人まで登録
	            for (Map.Entry<Integer, Long> rankEntry : rankCounts.entrySet()) {
	                int rankId = rankEntry.getKey();
	                long count = rankEntry.getValue();

	                if (rankId != 1) {
	                    totalShifts += (int) Math.min(count, 3 - totalShifts); // 最大3人まで登録
	                }
	            }
	        }

	        // 10. 日付毎の確定シフトテーブルに登録されるのは9人とする（省略）

	        // 11. 既に登録されているシフト数を取得（省略）
	        totalRegisteredShifts = shiftMapper.getTotalShiftsForWeek(startDate, endDate); // 値を更新
	        System.out.println("Total registered shifts: " + totalRegisteredShifts);

	        // 12. 新しい希望シフトを登録する前に、条件6を満たすかどうかを確認する（省略）
	        for (ShiftPreferences shiftPreferences : WeekUsersShiftPreferencesList) {
	            if (totalRegisteredShifts < 9) {
	                shiftMapper.WeekShiftsAdd(shiftPreferences);
	                totalRegisteredShifts++; // 新しいシフトが登録されたので、登録数を更新
	            } else {
	                break; // 条件6を満たしたので、ループを終了する
	            }
	        }
	    }
	}
}