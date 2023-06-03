package com.utopia.pmc.data.database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.utopia.pmc.data.dto.response.regimendetail.RegimenDetailResponse;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.RegimenDetail;
import com.utopia.pmc.mappers.RegimenDetailMapper;

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

    public static void removeRegimenResponse(String key, Set<Long> medicineIds) {
        if (!data.isEmpty()) {
            List<RegimenDetailResponse> regimenDetailResponses = data.get(key);
            regimenDetailResponses
                    .removeIf(regimenDetailResponse -> medicineIds.contains(regimenDetailResponse.getMedicineId()));
            updateData(key, regimenDetailResponses);
        }
    }

}
