package com.utopia.pmc.services.regimen;

import java.util.List;

import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;
import com.utopia.pmc.data.dto.response.regimen.RegimenResponse;

public interface RegimenService {
    public RegimenResponse createRegiment(RegimenRequest regimentRequest);
    public List<RegimenResponse> getRegimenOfCurrentUser();
    public void updateRegiment(Long regimentId);
}
