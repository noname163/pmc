package com.utopia.pmc.data.dto.request.user;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.utopia.pmc.data.constants.others.Gender;
import com.utopia.pmc.data.constants.others.Validation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserEditInforRequest {
    @NotBlank(message = "Username is require.")
    @Size(min = Validation.MIN_LENGTH_PASSWORD, message = "Username must more than 6 digit")
    private String username;
    @NotBlank(message = "Email is require.")
    @Pattern(regexp = Validation.EMAIL_REGEX, message = "Email must in format abc@domain.com")
    private String email;
    @NotNull(message = "Age is require")
    @Min(value = Validation.MIN_AGE, message = "Age cannot smaller than 16")
    @Max(value = Validation.MAX_AGE, message = "Age cannot bigger than 100")
    private Integer age;
    @NotNull(message = "Gender is require")
    private Gender gender;
    private String image;
}
