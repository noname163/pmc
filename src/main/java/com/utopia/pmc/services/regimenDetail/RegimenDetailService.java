package com.utopia.pmc.services.regimenDetail;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.utopia.pmc.data.constants.statuses.RegimentStatus;
import com.utopia.pmc.data.dto.request.regimen.EditRegimenRequest;
import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;
import com.utopia.pmc.data.dto.request.regimendetail.EditRegimenDetailRequest;
import com.utopia.pmc.data.dto.request.regimendetail.RegimenDetailNewMedicineRequest;
import com.utopia.pmc.data.dto.response.regimendetail.RegimenDetailResponse;

public interface RegimenDetailService {
    public void createRegimentDetails(RegimenRequest regimentRequest);

    public void reduceMedicineQuantity(Long regimenId, Set<Long> medicineId);

    public void editRegimenDetail(EditRegimenDetailRequest editRegimenDetailRequest);

    public Map<String, List<RegimenDetailResponse>> getRegimentDetailResponsesByStatusAndTime(
            RegimentStatus regimentStatus,
            LocalTime starTime,
            LocalTime endTime);

    public List<RegimenDetailResponse> getRegimenDetailResponses(Long regimenId);

    public RegimenDetailResponse getRegimenDetailResponse(Long regimenDetailId);
}
