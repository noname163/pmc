package com.utopia.pmc.services.regimen;

import java.util.List;

import com.utopia.pmc.data.constants.statuses.TakenStatus;
import com.utopia.pmc.data.dto.request.regimen.EditRegimenRequest;
import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;
import com.utopia.pmc.data.dto.response.regimen.RegimenResponse;
import com.utopia.pmc.data.entities.Regimen;

public interface RegimenService {
    public RegimenResponse createRegiment(RegimenRequest regimentRequest);
    public List<RegimenResponse> getRegimenOfCurrentUser();
    public void editRegimen(EditRegimenRequest editRegimenRequest);
    public void finishedRegimen(Long regimenId);
    public void deleteRegimen(Long regimenId);
    public void countTakenTimeOrMissedTime(Regimen regimen, TakenStatus takenStatus);
}
