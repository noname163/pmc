package com.utopia.pmc.services.medicine;

import java.util.List;

import com.utopia.pmc.data.entities.medicine.MedicationUse;
import com.utopia.pmc.data.entities.medicine.Medicine;

public interface UseOfMedicineService {
    public void createUseOfMedicine(Medicine medicine, List<MedicationUse> medicationUses);
}
