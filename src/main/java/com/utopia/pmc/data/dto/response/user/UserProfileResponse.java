package com.utopia.pmc.data.dto.response.user;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserProfileResponse {
    private String phoneNumber;
    private String email;
    private String planName;
    private LocalDate expriedDate;
}
