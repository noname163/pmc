package com.utopia.pmc.data.database;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.dto.response.regimen.RegimenNotificationResponse;
import com.utopia.pmc.data.entities.Regimen;
import com.utopia.pmc.data.entities.RegimenDetail;
import com.utopia.pmc.mappers.RegimenMapper;
import com.utopia.pmc.utils.RegimenFunction;

public class DailyData {

    private static Map<String, List<Object>> data;

    public static Map<String, List<Object>> getData(){
        return data;
    }

    public static void addDataset(String key, List<Object> values){
        data.put(key, values);
    }

    public static void addData(String key, Object value){
        data.get(key).add(value);
    }

    public static void removeData(String key){
        data.remove(key);
    }

    public static void addRegimen(Regimen regimen){
        RegimenFunction regimenFunction = new RegimenFunction();
        RegimenMapper regimenMapper = new RegimenMapper();
        List<RegimenDetail> regimentDetails = regimen.getRegimentDetails();
        for (RegimenDetail regimentDetail : regimentDetails) {
            LocalTime takenTime = regimenFunction.determineTakenTime(regimentDetail);
            if(takenTime!=null && RegimentStatus.INPROCESS.equals(regimen.getStatus())){
                RegimenNotificationResponse regimenNotificationResponse = regimenMapper.mapEntityToNotificationResponse(regimen);
                regimenNotificationResponse.setTakenTime(takenTime.toString());
                addData(takenTime.toString(), regimenNotificationResponse);
            }
        }
    }
}
