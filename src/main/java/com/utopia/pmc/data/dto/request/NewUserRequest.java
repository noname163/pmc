package com.utopia.pmc.data.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.constants.others.Gender;
import com.utopia.pmc.data.constants.others.Validation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NewUserRequest {
    @NotBlank(message = "Username is require.")
    @Min(value = Validation.MIN_LENGTH_PASSWORD, message = "Username must more than 6 digit")
    private String username;
    @NotBlank(message = "Password is require.")
    @Min(value = Validation.MIN_LENGTH_PASSWORD, message = "Password must more than 6 digit")
    private String password;
    @NotBlank(message = "Email is require")
    @Email(regexp = Validation.EMAIL_REGEX, message = "Email must in format abc@domain.com")
    private String email;
    @NotBlank(message = "Phone number is require")
    @Pattern(regexp = Validation.PHONE_REGEX, message = "Phone number must have 10 number")
    private String phone;
    @NotBlank(message = "Age is require")
    @Size(min = Validation.MIN_AGE, max = Validation.MAX_AGE, message = "Age must in range of 16 to 100")
    private Integer age;
    @NotBlank(message = "Gender is require")
    private Gender gender;
}
