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
    private ConvertStringToLocalTime convertStringToLocalTime;

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
    public LocalTime determineTakenTime(LocalTime firstTime, LocalTime secondTime, LocalTime thirdTime, LocalTime fourthTime) {
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

    public LocalTime convertStringToLocalTime(String time){
        return convertStringToLocalTime.convertStringToLocalTime(Validation.TIME_FORMAT, time);
    }

}
