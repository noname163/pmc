package com.utopia.pmc.services.user;

import com.utopia.pmc.data.dto.request.user.NewUserRequest;

public interface UserService {
    public void createUser(NewUserRequest newUserRequest);
}
