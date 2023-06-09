package com.utopia.pmc.services.authenticate.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.utopia.pmc.config.CustomUserDetails;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.UserRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.ForbiddenException;
import com.utopia.pmc.services.authenticate.SecurityContextService;

@Service
public class SecurityContextServiceImpl implements SecurityContextService {
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void setSecurityContext(String phonenumber) {
        Optional<User> userOptional = userRepository.findByPhone(phonenumber);
        if (userOptional.isEmpty()) {
            throw new ForbiddenException("Invalid username in JWT.");
        }
        UserDetails userDetails = new CustomUserDetails(userOptional.get());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        securityContext.setAuthentication(usernamePasswordAuthenticationToken);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        return ((CustomUserDetails) principal).getUser();
    }

    @Override
    public void validateCurrentUser(User user) {
        User currentUser = getCurrentUser();
        if(!currentUser.getPhone().equals(user.getPhone())){
            throw new BadRequestException("Invalid User");
        }
    }
}
