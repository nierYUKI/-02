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

    @Override
    public List<ShiftPreferences> getShiftByDate(LocalDate selectDate) {
        // Step 1: 日付毎の希望シフトを取得する
        List<ShiftPreferences> shiftPreferencesList = shiftPreferencesMapper.selectShiftByDate(selectDate);
        List<ShiftPreferences> shiftList = new ArrayList<>();
        // Step 2: 各時間帯ごとに希望シフトをグループ化し、各グループ内で各ランクのアルバイト数を数える
        Map<LocalTime, Map<Integer, Long>> shiftByTimeAndRank = shiftPreferencesList.stream()
                .collect(Collectors.groupingBy(ShiftPreferences::getStartTime, // 時間帯ごとにグループ化
                         Collectors.groupingBy(ShiftPreferences::getRankId, Collectors.counting()))); // 各ランクのアルバイト数を数える
        
        // Step 3: 各時間帯ごとに処理を行う
        shiftByTimeAndRank.forEach((startTime, rankCountMap) -> {
        	for(Map.Entry<Integer, Long> entry: rankCountMap.entrySet()) {
        		
        		System.out.println("entry.getKey()->"+entry.getKey());//ここがランクIDの値
        		System.out.println("entry.getValue()->"+entry.getValue());//ランクIDの値の人数
            //アルバイトランク「アイアン」が同じ時間帯にいるかどうか確認
            List<ShiftPreferences> ironShifts = shiftPreferencesList.stream()
                    .filter(shift -> shift.getStartTime().equals(startTime) && shift.getRankId() == shiftPreferencesList.get(0).getRankId())
                    .collect(Collectors.toList());
            //ランクアイアンが同じ時間帯にいた場合
            //PreferenceIdの若い方が確定シフトに登録される記述
            if (ironShifts.size() > 1) {
                ShiftPreferences youngestIronShift = ironShifts.stream()
                        .min(Comparator.comparing(ShiftPreferences::getPreferenceId))
                        .orElse(null);

                if (youngestIronShift != null) {
								/*shiftMapper.ShiftsAdd(youngestIronShift);*/
                	shiftList.add(youngestIronShift);
                }
            }
        }
    });

    // Step 4: 処理後のリストを返す
    return shiftList;
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
    public void addShifts(Shifts shifts) {
        // TODO 自動生成されたメソッド・スタブ

    }

    // その他のメソッドやクラスの定義...

}
