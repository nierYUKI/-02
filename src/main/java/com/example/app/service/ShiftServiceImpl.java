package com.example.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.ShiftPreferences;
import com.example.app.domain.Shifts;
import com.example.app.mapper.JobRankMapper;
import com.example.app.mapper.ShiftPreferencesMapper;

@Service
public class ShiftServiceImpl implements ShiftService {

    private final ShiftPreferencesMapper shiftPreferencesMapper;
    private final JobRankMapper jobRankMapper;

    @Autowired
    public ShiftServiceImpl(ShiftPreferencesMapper shiftPreferencesMapper, JobRankMapper jobRankMapper) {
        this.shiftPreferencesMapper = shiftPreferencesMapper;
        this.jobRankMapper = jobRankMapper;
    }

    @Override
    public List<ShiftPreferences> getShiftByDate(LocalDate selectDate) {
        // Step 1: 日付毎の希望シフトを取得する
        List<ShiftPreferences> shiftPreferencesList = shiftPreferencesMapper.selectShiftByDate(selectDate);

        // Step 2: 各時間帯ごとに希望シフトをグループ化し、各グループ内で各ランクのアルバイト数を数える
        Map<LocalTime, Map<Integer, Long>> shiftByTimeAndRank = shiftPreferencesList.stream()
                .collect(Collectors.groupingBy(ShiftPreferences::getStartTime, // 時間帯ごとにグループ化
                         Collectors.groupingBy(ShiftPreferences::getRankId, Collectors.counting()))); // 各ランクのアルバイト数を数える
        
        // Step 3: 各時間帯ごとに処理を行う
        shiftByTimeAndRank.forEach((startTime, rankCountMap) -> {
        	for(Map.Entry<Integer, Long> entry: rankCountMap.entrySet()) {
        		System.out.println("entry.getKey()->"+entry.getKey());
        		System.out.println("entry.getValue()->"+entry.getValue());
        	}
//            // アイアンランクのアルバイトが2人以上同じグループ内にいる場合、1人を別の時間帯に移動させる
//            if (rankCountMap.containsKey(shiftPreferencesList.get(0).getRankId()) && rankCountMap.get(shiftPreferencesList.get(0).getRankId()) > 1) {
//                // アルバイトを移動させるメソッドを呼び出す
//                adjustShiftsInGroup(shiftPreferencesList, startTime);
//            }
        });

        // Step 4: 処理後のリストを返す
        return shiftPreferencesList;
    }

    // 必要に応じてアルバイトを移動させるメソッド
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

    @Override
    public void addShifts(Shifts shifts) {
        // TODO 自動生成されたメソッド・スタブ

    }

    // その他のメソッドやクラスの定義...

}
