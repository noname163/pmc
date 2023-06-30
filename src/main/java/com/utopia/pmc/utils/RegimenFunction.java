package com.utopia.pmc.utils;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utopia.pmc.data.constants.others.Period;
import com.utopia.pmc.data.constants.others.Validation;
import com.utopia.pmc.data.entities.RegimenDetail;

@Component
public class RegimenFunction {

    @Autowired
    private ConvertStringToLocalDateTime convertStringToLocalTime;

    public LocalTime determineTakenTime(RegimenDetail regimenDetail) {
        LocalTime currentTime = LocalTime.now();
        if (regimenDetail.getFirstTime() != null && currentTime.compareTo(regimenDetail.getFirstTime()) <= 0) {
            return regimenDetail.getFirstTime();
        }
        if (regimenDetail.getSecondTime() != null && currentTime.compareTo(regimenDetail.getSecondTime()) <= 0) {
            return regimenDetail.getSecondTime();
        }
        if (regimenDetail.getThirdTime() != null && currentTime.compareTo(regimenDetail.getThirdTime()) <= 0) {
            return regimenDetail.getThirdTime();
        }
        if (regimenDetail.getFourthTime() != null && currentTime.compareTo(regimenDetail.getFourthTime()) <= 0) {
            return regimenDetail.getFourthTime();
        }
        return null;
    }

    public LocalTime determineTakenTime(LocalTime firstTime, LocalTime secondTime, LocalTime thirdTime,
            LocalTime fourthTime) {
        LocalTime currentTime = LocalTime.now();

        if (firstTime != null && currentTime.compareTo(firstTime.plusMinutes(5)) <= 0) {
            return firstTime;
        }
        if (secondTime != null && currentTime.compareTo(secondTime.plusMinutes(5)) <= 0) {
            return secondTime;
        }
        if (thirdTime != null && currentTime.compareTo(thirdTime.plusMinutes(5)) <= 0) {
            return thirdTime;
        }
        if (fourthTime != null && currentTime.compareTo(fourthTime.plusMinutes(5)) <= 0) {
            return fourthTime;
        }
        return null;
    }

    public int calculateMedicineQuantity(int takenQuantity, int takenTimePerDay, int doseRegimen, Period period) {
        switch (period) {
            case DAY:
                return (takenQuantity * takenTimePerDay) * doseRegimen;
            case WEEK:
                return (takenQuantity * takenTimePerDay) * (doseRegimen * 7);
            case MONTH:
                return (takenQuantity * takenTimePerDay) * (doseRegimen * LocalDate.now().getDayOfMonth());
            default:
                return (takenQuantity * takenTimePerDay) * (doseRegimen * LocalDate.now().getDayOfYear());

        }

    }

    public int calculateTakenTimePerDay(RegimenDetail regimenDetail) {
        int result = 0;
        if (regimenDetail.getFirstTime() != null)
            result++;
        if (regimenDetail.getSecondTime() != null)
            result++;
        if (regimenDetail.getThirdTime() != null)
            result++;
        if (regimenDetail.getFourthTime() != null)
            result++;
        return result;
    }

    public LocalTime convertStringToLocalTime(String time) {
        return convertStringToLocalTime.convertStringToLocalTime(Validation.TIME_FORMAT, time);
    }

}
