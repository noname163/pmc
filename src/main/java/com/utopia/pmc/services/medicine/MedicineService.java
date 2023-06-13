package com.utopia.pmc.services.medicine;

import java.util.List;

import com.utopia.pmc.data.dto.request.medicine.MedicineRequest;
import com.utopia.pmc.data.dto.request.medicine.UserMedicineRequest;
import com.utopia.pmc.data.dto.response.medicine.SearchMedicineResponse;

public interface MedicineService {
    public void createNewMedicine(MedicineRequest medicineRequest);
    public Long userCreateNewMedicine(UserMedicineRequest userMedicineRequest);
    public List<SearchMedicineResponse> searchMedicineByName(String name);
}
