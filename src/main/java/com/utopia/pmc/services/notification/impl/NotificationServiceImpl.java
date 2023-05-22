package com.utopia.pmc.services.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.utopia.pmc.data.repositories.RegimentDetailRepository;
import com.utopia.pmc.data.repositories.RegimentRepository;
import com.utopia.pmc.services.notification.NotificationService;

public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private RegimentRepository regimentRepository;
    @Autowired 
    private RegimentDetailRepository regimentDetailRepository;

    
    @Override
    public void remindMedicineScheduler() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remindMedicineScheduler'");
    }
    
}
