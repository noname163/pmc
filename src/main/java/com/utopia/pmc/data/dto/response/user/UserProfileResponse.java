package com.utopia.pmc.data.dto.response.user;

import java.time.LocalDate;

import com.utopia.pmc.data.constants.others.Gender;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserProfileResponse {
    private String phoneNumber;
    private String email;
    private String username;
    private Integer age;
    private Gender gender;
    private String image;
    private String planName;
    private LocalDate expriedDate;
}
