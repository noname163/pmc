package com.utopia.pmc.services.regimen;

import com.utopia.pmc.data.dto.request.regimen.RegimenRequest;

public interface RegimenService {
    public void createRegiment(RegimenRequest regimentRequest);
    public void updateRegiment(Long regimentId);
}
