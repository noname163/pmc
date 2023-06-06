package com.utopia.pmc.services.medicine.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.entities.medicine.MedicationUse;
import com.utopia.pmc.data.entities.medicine.Medicine;
import com.utopia.pmc.data.entities.medicine.UseOfMedicine;
import com.utopia.pmc.data.entities.medicine.UseOfMedicineKey;
import com.utopia.pmc.data.repositories.medicine.UseOfMedicineRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.services.medicine.UseOfMedicineService;

@Service
public class UseOfMedicineServiceImpl implements UseOfMedicineService {
    @Autowired
    private UseOfMedicineRepository useOfMedicineRepository;
    @Autowired
    private Message message;

    @Override
    @Transactional
    public void createUseOfMedicine(Medicine medicine, List<MedicationUse> medicationUses) {

        if (medicine == null) {
            throw new BadRequestException(message.badValue("Medicine"));
        }
        if (medicationUses == null || medicationUses.isEmpty()) {
            throw new BadRequestException(message.emptyList("Medication use"));
        }

        List<UseOfMedicine> useOfMedicines = new ArrayList<>();

        for (MedicationUse medicationUse : medicationUses) {
            UseOfMedicineKey useOfMedicineKey = UseOfMedicineKey.builder().medicationUseId(medicationUse.getId())
                    .medicineId(medicine.getId()).build();
            UseOfMedicine useOfMedicine = UseOfMedicine.builder()
                    .id(useOfMedicineKey)
                    .medicine(medicine)
                    .medicationUse(medicationUse)
                    .build();
            useOfMedicines.add(useOfMedicine);
        }

        useOfMedicineRepository.saveAll(useOfMedicines);
    }

}
