package com.utopia.pmc.services.authenticate;

import com.utopia.pmc.data.dto.request.UserLoginRequest;
import com.utopia.pmc.data.dto.response.UserLoginResponse;

public interface AuthenticationService {
    public UserLoginResponse login(UserLoginRequest userLoginRequest);
}
