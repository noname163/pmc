package com.utopia.pmc.services.medicine.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.dto.request.medicine.MedicineRequest;
import com.utopia.pmc.data.dto.response.medicine.SearchMedicineResponse;
import com.utopia.pmc.data.entities.medicine.DosageForm;
import com.utopia.pmc.data.entities.medicine.MedicationUse;
import com.utopia.pmc.data.entities.medicine.Medicine;
import com.utopia.pmc.data.entities.medicine.MedicineClassification;
import com.utopia.pmc.data.repositories.medicine.DosageFormRepository;
import com.utopia.pmc.data.repositories.medicine.MedicationUseRepository;
import com.utopia.pmc.data.repositories.medicine.MedicineClassificationRepository;
import com.utopia.pmc.data.repositories.medicine.MedicineRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.mappers.MedicineMapper;
import com.utopia.pmc.services.medicine.MedicineService;
import com.utopia.pmc.services.medicine.UseOfMedicineService;

@Service
public class MedicineServiceImpl implements MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private MedicationUseRepository medicationUseRepository;
    @Autowired
    private DosageFormRepository dosageFormRepository;
    @Autowired
    private UseOfMedicineService useOfMedicineService;
    @Autowired
    private MedicineClassificationRepository medicineClassificationRepository;
    @Autowired
    private MedicineMapper medicineMapper;
    @Autowired
    private Message message;

    @Override
    @Transactional
    public void createNewMedicine(MedicineRequest medicineRequest) {
        Optional<Medicine> medicineOtp = medicineRepository.findByName(medicineRequest.getName());

        medicineOtp.ifPresent(medicine -> {
            throw new BadRequestException(message.objectExistMessage("Medicine", medicineRequest.getName()));
        });

        Medicine medicine = medicineMapper.mapDtoToEntity(medicineRequest);

        List<MedicationUse> medicationUses = medicationUseRepository.findByIdIn(medicineRequest.getUseOfMedicineIds());
        MedicineClassification medicineClassification = medicineClassificationRepository
                .findById(medicineRequest.getClassificationId()).orElseThrow(() -> new BadRequestException(
                        message.objectNotFoundByIdMessage("Classification", medicineRequest.getClassificationId())));
        DosageForm dosageForm = dosageFormRepository.findByFormIgnoreCase(medicineRequest.getDosageForm())
                .orElseThrow(() -> new BadRequestException(
                        message.objectNotFoundByIdMessage("Dosage Form", medicineRequest.getDosageForm())));

        medicine.setDosageForm(dosageForm);
        medicine.setClassification(medicineClassification);

        medicine = medicineRepository.save(medicine);

        useOfMedicineService.createUseOfMedicine(medicine, medicationUses);
    }

    @Override
    public List<SearchMedicineResponse> searchMedicineByName(String name) {

        List<Medicine> medicines = medicineRepository.findByNameContainingIgnoreCase(name)
                .orElseThrow(() -> new BadRequestException(message.emptyList("Medicines")));

        return medicineMapper.mapEntitiesToDtos(medicines);
    }

}
