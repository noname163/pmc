package com.utopia.pmc.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.utopia.pmc.filters.AuthenticationFilter;

@Configuration
public class FilterConfig {

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter();
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistration(AuthenticationFilter authenticationFilter) {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>(authenticationFilter);
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

}

