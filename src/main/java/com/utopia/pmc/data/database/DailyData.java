package com.utopia.pmc.data.database;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.utopia.pmc.data.constants.others.Validation;
import com.utopia.pmc.data.dto.response.regimendetail.RegimenDetailResponse;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.RegimenDetail;
import com.utopia.pmc.mappers.RegimenDetailMapper;
import com.utopia.pmc.utils.RegimenFunction;

public class DailyData {

    private static Map<String, List<RegimenDetailResponse>> data = new HashMap<>();

    public static Map<String, List<RegimenDetailResponse>> getData() {
        return data;
    }

    public static void addDataset(String key, List<RegimenDetailResponse> list) {
        data.put(key, list);
    }

    public static void addData(String key, RegimenDetailResponse value) {
        data.get(key).add(value);
    }

    public static void updateData(String key, List<RegimenDetailResponse> values) {
        data.replace(key, values);
    }

    public static void removeData(String key) {
        data.remove(key);
    }

    public static void addRegimen(Regimen regimen) {
        List<RegimenDetail> regimentDetails = regimen.getRegimentDetails();
        RegimenDetailMapper regimenDetailMapper = new RegimenDetailMapper();
        addDataset(regimen.getDeviceToken(), regimenDetailMapper.mapEntityToDtos(regimentDetails));
    }

    public static void addRegimenDetail(String takenTime, List<RegimenDetailResponse> regimenDetailResponses) {
        
        if(data.get(takenTime)!=null){
            List<RegimenDetailResponse> newRegimenDetailResponses = data.get(takenTime);
            for (RegimenDetailResponse regimenDetailResponse : regimenDetailResponses) {
                newRegimenDetailResponses.add(regimenDetailResponse);
            }
            data.replace(takenTime, newRegimenDetailResponses);
        }
        data.putIfAbsent(takenTime, data.put(takenTime, regimenDetailResponses));

    }

    public static void removeRegimenResponse(LocalTime key, Set<Long> medicineIds) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Validation.TIME_FORMAT_WITH_SECOND);
        if (!data.isEmpty()) {
            List<RegimenDetailResponse> regimenDetailResponses = data.get(key.format(formatter));
            regimenDetailResponses
                    .removeIf(regimenDetailResponse -> medicineIds.contains(regimenDetailResponse.getMedicineId()));
            updateData(key.format(formatter), regimenDetailResponses);
        }
    }

}
