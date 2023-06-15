package com.utopia.pmc.services.user;

import com.utopia.pmc.data.dto.request.user.NewUserRequest;
import com.utopia.pmc.data.entities.PaymentPlan;
import com.utopia.pmc.data.entities.User;

public interface UserService {
    public void createUser(NewUserRequest newUserRequest);
    
    public void upgradePaymenPlan(User user, PaymentPlan paymentPlan);
}
