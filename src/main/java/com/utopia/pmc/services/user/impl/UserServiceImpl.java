package com.utopia.pmc.services.user.impl;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.utopia.pmc.data.constants.others.Period;
import com.utopia.pmc.data.constants.others.Role;
import com.utopia.pmc.data.dto.request.user.NewUserRequest;
import com.utopia.pmc.data.entities.PaymentPlan;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.UserRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.mappers.UserMapper;
import com.utopia.pmc.services.authenticate.SecurityContextService;
import com.utopia.pmc.services.user.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Message message;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SecurityContextService securityContextService;

    @Override
    public void createUser(NewUserRequest newUserRequest) {
        Optional<User> userOtp = userRepository.findByPhone(newUserRequest.getPhone());
        if (userOtp.isPresent()) {
            throw new BadRequestException(message.objectExistMessage("Phonenumber", newUserRequest.getPhone()));
        }
        User user = userMapper.mapDtoToEntity(newUserRequest);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(newUserRequest.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void upgradePaymenPlan(User user, PaymentPlan paymentPlan) {
        
        user.setPaymentPlan(paymentPlan);
        LocalDate currentDate = LocalDate.now();
        Integer expriedTime = paymentPlan.getExpriredTime();
        Period period = paymentPlan.getPeriod();

        switch (period) {
            case DAY:
                user.setPaymentExpriedDate(currentDate.plusDays(expriedTime));
                break;
            case WEEK:
                user.setPaymentExpriedDate(currentDate.plusWeeks(expriedTime));
                break;
            case MONTH:
                user.setPaymentExpriedDate(currentDate.plusMonths(expriedTime));
                break;
            default:
                user.setPaymentExpriedDate(currentDate.plusYears(expriedTime));
                break;

        }
        userRepository.save(user);
    }

}
