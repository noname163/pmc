package com.utopia.pmc.utils;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.entities.RegimentDetail;

@Component
public class DetermineTakenTime {

    public LocalTime determineTakenTime(RegimentDetail regimentDetail) {
        if (regimentDetail.getFirstTime() != null) {
            return regimentDetail.getFirstTime();
        }
        if (regimentDetail.getSecondTime() != null) {
            return regimentDetail.getSecondTime();
        }
        if (regimentDetail.getThirdTime() != null) {
            return regimentDetail.getThirdTime();
        }
        if (regimentDetail.getFourthTime() != null) {
            return regimentDetail.getFourthTime();
        }
        return null;
    }
}
