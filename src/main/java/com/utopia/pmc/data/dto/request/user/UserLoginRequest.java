package com.utopia.pmc.data.dto.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {
    private String phonenumber;
    private String password;
}
