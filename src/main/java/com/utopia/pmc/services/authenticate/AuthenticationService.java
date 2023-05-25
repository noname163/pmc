package com.utopia.pmc.services.authenticate;

import com.utopia.pmc.data.dto.request.user.UserLoginRequest;
import com.utopia.pmc.data.dto.response.user.UserLoginResponse;

public interface AuthenticationService {
    public UserLoginResponse login(UserLoginRequest userLoginRequest);
}
