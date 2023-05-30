package com.utopia.pmc.services.regimen;

import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;
import com.utopia.pmc.data.dto.response.regimen.RegimenResponse;

public interface RegimenService {
    public RegimenResponse createRegiment(RegimenRequest regimentRequest);
    public void updateRegiment(Long regimentId);
}
