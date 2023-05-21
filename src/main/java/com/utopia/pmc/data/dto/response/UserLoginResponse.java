package com.utopia.pmc.data.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserLoginResponse {
    private String accessToken;
    private String refreshToken;
}
