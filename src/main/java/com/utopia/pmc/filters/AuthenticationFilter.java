package com.utopia.pmc.filters;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.ForbiddenException;
import com.utopia.pmc.services.authenticate.SecurityContextService;
import com.utopia.pmc.utils.JwtTokenUtil;

import org.springframework.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class AuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SecurityContextService securityContextService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/swagger-ui")
                || request.getRequestURI().startsWith("/v3/api-docs")
                || request.getRequestURI().startsWith("/api/login")) {
            filterChain.doFilter(request, response);
        } else {
            final Optional<String> requestTokenHeaderOpt = getJwtFromRequest(request);
            if (requestTokenHeaderOpt.isPresent()) {
                try {
                    String accessToken = requestTokenHeaderOpt.get();
                    Jws<Claims> listClaims = jwtTokenUtil.getJwsClaims(accessToken);
                    // String username = jwtTokenUtil.getUsernameFromClaims(listClaims.getBody());
                    String phone = jwtTokenUtil.getPhoneFromClaims(listClaims.getBody());
                    securityContextService.setSecurityContext(phone);
                    filterChain.doFilter(request, response);
                } catch (Exception ex) {
                    throw new BadRequestException(ex.getMessage(), ex);
                }
            } else {
                throw new ForbiddenException("JWT Access Token does not start with 'Bearer '.");
            }
        }
    }

    private Optional<String> getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }
}
