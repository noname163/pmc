package com.utopia.pmc.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.request.NewUserRequest;
import com.utopia.pmc.data.entities.User;

@Component
public class UserMapper {
    public User mapDtoToEntity(NewUserRequest newUserRequest) {
        return User.builder()
                .age(newUserRequest.getAge())
                .email(newUserRequest.getEmail())
                .gender(newUserRequest.getGender())
                .username(newUserRequest.getUsername())
                .password(newUserRequest.getPassword())
                .phone(newUserRequest.getPhone())
                .build();
    }

    public List<User> mapDtoToEntity(List<NewUserRequest> newUserRequests) {
        return newUserRequests
                .stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }
}
