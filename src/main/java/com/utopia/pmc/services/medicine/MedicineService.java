package com.utopia.pmc.services.medicine;

import java.util.List;

import com.utopia.pmc.data.dto.request.medicine.MedicineRequest;
import com.utopia.pmc.data.dto.response.medicine.SearchMedicineResponse;

public interface MedicineService {
    public void createNewMedicine(MedicineRequest medicineRequest);
    public List<SearchMedicineResponse> searchMedicineByName(String name);
}
