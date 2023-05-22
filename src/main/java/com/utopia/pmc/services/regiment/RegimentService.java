package com.utopia.pmc.services.regiment;

import com.utopia.pmc.data.dto.request.RegimentRequest;

public interface RegimentService {
    public void createRegiment(RegimentRequest regimentRequest);
    public void updateRegiment(Long regimentId);
}
