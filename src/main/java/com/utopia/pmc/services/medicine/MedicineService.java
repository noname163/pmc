package com.utopia.pmc.services.medicine;

import com.utopia.pmc.data.dto.request.medicine.MedicineRequest;

public interface MedicineService {
    public void createNewMedicine(MedicineRequest medicineRequest);
}
