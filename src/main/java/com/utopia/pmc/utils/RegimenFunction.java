package com.utopia.pmc.utils;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.constants.others.Period;
import com.utopia.pmc.data.entities.RegimentDetail;

@Component
public class RegimenFunction {

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

    public Integer calculateMedicineQuantity(Integer takenQuantity, Integer doseRegimen, Period period) {
        switch (period) {
            case DAY:
                return takenQuantity * doseRegimen;
            case WEEK:
                return takenQuantity * (doseRegimen * 7);
            case MONTH:
                return takenQuantity * (doseRegimen * LocalDate.now().getDayOfMonth());
            default:
                return takenQuantity * (doseRegimen * LocalDate.now().getDayOfYear());

        }

    }
}
