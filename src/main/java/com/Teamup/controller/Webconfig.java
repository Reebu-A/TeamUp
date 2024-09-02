package com.Teamup.controller;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Webconfig {

    @Bean
    public FilterRegistrationBean<NoCacheFilter> noCacheFilter() {
        FilterRegistrationBean<NoCacheFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new NoCacheFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Integer.MAX_VALUE); // Ensure it runs after other filters
        return registrationBean;
    }
}
