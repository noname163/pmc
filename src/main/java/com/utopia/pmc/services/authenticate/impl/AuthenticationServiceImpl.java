package com.utopia.pmc.services.authenticate.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.dto.request.user.UserLoginRequest;
import com.utopia.pmc.data.dto.response.user.UserLoginResponse;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.UserRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.services.authenticate.AuthenticationService;
import com.utopia.pmc.utils.JwtTokenUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Message message;

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        Optional<User> userOtp = userRepository.findByPhone(userLoginRequest.getPhonenumber());
        if (userOtp.isEmpty()) {
            throw new BadRequestException(message.objectNotFoundByIdMessage("User", userLoginRequest.getPhonenumber()));
        }
        User user = userOtp.get();
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Username or password is incorrect. Please try again");
        }
        String accessToken = jwtTokenUtil.generateJwtToken(user, 1000);
        String refreshToken = jwtTokenUtil.generateJwtToken(user, 10000);
        return UserLoginResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();

    }

    @Override
    public UserLoginResponse refreshToken(String refreshTokenRequest) {
        Jws<Claims> listClaims = jwtTokenUtil.getJwsClaims(refreshTokenRequest);
        String phone = jwtTokenUtil.getPhoneFromClaims(listClaims.getBody());

        Optional<User> userOtp = userRepository.findByPhone(phone);
        if (userOtp.isEmpty()) {
            throw new BadRequestException(message.objectNotFoundByIdMessage("User", phone));
        }
        User user = userOtp.get();

        String accessToken = jwtTokenUtil.generateJwtToken(user, 1000);
        String refreshToken = jwtTokenUtil.generateJwtToken(user, 10000);
        
        return UserLoginResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

}
