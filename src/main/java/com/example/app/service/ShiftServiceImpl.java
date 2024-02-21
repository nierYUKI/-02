package com.example.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
    public ShiftServiceImpl(ShiftPreferencesMapper shiftPreferencesMapper, JobRankMapper jobRankMapper,ShiftMapper shiftMapper) {
        this.shiftPreferencesMapper = shiftPreferencesMapper;
        this.jobRankMapper = jobRankMapper;
        this.shiftMapper = shiftMapper;
    }

    //List<ShiftPreferences>
    @Override
    public void getShiftByDate(LocalDate selectDate) {
  		// 1. 指定された日付でデータベースからシフトの希望リストを取得
  		List<ShiftPreferences> UsersShiftPreferencesList = shiftPreferencesMapper.selectShiftByDate(selectDate);
  		/*System.out.println(UsersShiftPreferencesList);*/

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
  						
  					// 8. 加工したデータのリスト（shiftList）を元のデータに組み込む
  						for (ShiftPreferences ironShift : shiftList) {
  						    // もし元のデータに加工したデータのpreferenceIdと同じものがなければ、追加する
  						    if (!UsersShiftPreferencesList.contains(ironShift)) {
  						        UsersShiftPreferencesList.add(ironShift);
  						    }
  						}

  						// 9. 元のデータから、加工したデータの要素と同じ条件を満たす要素を削除する
  						UsersShiftPreferencesList.removeIf(shift -> shiftList.contains(shift));

  					}
  				}
  			}
  		});

    // 10. 処理後のリストを返す
//    return UsersShiftPreferencesList;
  		for(ShiftPreferences shiftPreferences : UsersShiftPreferencesList) {
  			//System.out.println(shiftPreferences);
  			shiftMapper.ShiftsAdd(shiftPreferences);
  		}
}

/*    // 必要に応じてアルバイトを移動させるメソッド
  private void adjustShiftsInGroup(List<ShiftPreferences> shiftPreferencesList, LocalTime startTime) {
      // 同じ開始時間でアイアンランクのアルバイトを抽出
      List<ShiftPreferences> ironShifts = shiftPreferencesList.stream()
      		
              .filter(shift -> shift.getStartTime().equals(startTime) && shift.getRankId() == shiftPreferencesList.get(0).getRankId())
              .collect(Collectors.toList());

      // アルバイトが2人以上の場合、preferenceId(主キー)の値が若い方を確定シフトに登録する
      if (ironShifts.size() > 1) {
          // preferenceId(主キー)の値が若い方を取得
          ShiftPreferences youngestIronShift = ironShifts.stream()
                  .min(Comparator.comparing(ShiftPreferences::getPreferenceId))
                  .orElse(null);

          // 移動後のシフトをデータベースに保存する
          if (youngestIronShift != null) {
              shiftPreferencesMapper.updateShiftPreference(youngestIronShift);
          }
      }
  }
*/

    @Override
    //確定テーブルに登録した日付ごとのデータ一覧取得
		public List<Shifts> getselectShiftAll(LocalDate selectDate) {
        List<Shifts>selectShifts = shiftMapper.selectShiftAll(selectDate);
//        System.out.println(selectShifts);
				return selectShifts;


    }

		@Override
		public void getweekShiftDate(LocalDate startDate, LocalDate endDate) {
	  	//範囲指定した日付でデータベースからシフトの希望リストを取得
  		List<ShiftPreferences>WeekUsersShiftPreferencesList = shiftPreferencesMapper.weekShiftDate(startDate,endDate);
  		
  		System.out.println(WeekUsersShiftPreferencesList);
  		
  		
  		for(ShiftPreferences shiftPreferences : WeekUsersShiftPreferencesList) {
  			//System.out.println(shiftPreferences);
  			shiftMapper.WeekShiftsAdd(shiftPreferences);
  		}
  		
		}

    // その他のメソッドやクラスの定義...

}
