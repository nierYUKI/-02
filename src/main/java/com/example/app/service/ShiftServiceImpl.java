package com.example.app.service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.ShiftPreferences;
import com.example.app.domain.Shifts;
import com.example.app.mapper.ShiftPreferencesMapper;

@Service
public class ShiftServiceImpl implements ShiftService {
	
	//必要な情報
	//カレンダーの日付、開始時間、ユーザー名、ランクID、

    @Autowired
    private ShiftPreferencesMapper shiftPreferencesMapper;

    public void adjustShiftsForIronWorkers(LocalDate selectDate) {
        // 1. 日付毎の希望シフトを取得
        List<ShiftPreferences> shifts = shiftPreferencesMapper.selectShiftByDate(selectDate);

        // 2. 同じ開始時間の希望シフトをグループ化
        Map<LocalTime, List<ShiftPreferences>> shiftGroups = groupShiftsByStartTime(shifts);

        // 3. 同じグループ内でアイアン同士が同時に働いているか確認し、必要に応じて調整
        for (List<ShiftPreferences> group : shiftGroups.values()) {
            checkAndAdjustIronWorkers(group);
        }

        // 4. 移動させた希望シフトを更新し、データベースに保存
        for (ShiftPreferences shift : shifts) {
            shiftPreferencesMapper.updateShiftPreference(shift);
        }
    }

    private Map<LocalTime, List<ShiftPreferences>> groupShiftsByStartTime(List<ShiftPreferences> shifts) {
			return null;
        // 同じ開始時間で希望シフトをグループ化する処理を実装
    }

    private void checkAndAdjustIronWorkers(List<ShiftPreferences> shifts) {
        // 同じグループ内でアイアン同士が同時に働いているかを確認し、必要に応じて調整する処理を実装
    }

		@Override
		public List<ShiftPreferences> getShiftByDate(LocalDate selectDate) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public void addShifts(Shifts shifts) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
}
