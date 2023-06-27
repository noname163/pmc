package com.utopia.pmc.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.request.user.NewUserRequest;
import com.utopia.pmc.data.dto.response.user.UserProfileResponse;
import com.utopia.pmc.data.entities.User;

@Component
public class UserMapper {
    public User mapDtoToEntity(NewUserRequest newUserRequest) {
        return User.builder()
                .email(newUserRequest.getEmail())
                .phone(newUserRequest.getPhone())
                .build();
    }

    public UserProfileResponse mapEntityToDto(User user) {
        return UserProfileResponse
                .builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhone())
                .expriedDate(user.getPaymentExpriedDate())
                .planName(user.getPaymentPlan() != null ? user.getPaymentPlan().getName() : null)
                .build();
    }

    public List<User> mapDtosToEntities(List<NewUserRequest> newUserRequests) {
        return newUserRequests
                .stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }
}
