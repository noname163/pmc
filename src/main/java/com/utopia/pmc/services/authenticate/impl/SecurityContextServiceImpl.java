package com.utopia.pmc.services.authenticate.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;

import com.utopia.pmc.config.CustomUserDetails;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.UserRepository;
import com.utopia.pmc.exceptions.ForbiddenException;
import com.utopia.pmc.services.authenticate.SecurityContextService;

public class SecurityContextServiceImpl implements SecurityContextService {
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void setSecurityContext(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
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
}
