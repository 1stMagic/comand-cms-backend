package com.biock.cms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf().disable()
                .authorizeRequests().anyRequest().permitAll();
    }

    //    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//
//        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
//    }

//    private final CmsUserDetailsService cmsUserDetailsService;
//
//    public SecurityConfigurer(final CmsUserDetailsService cmsUserDetailsService) {
//
//        this.cmsUserDetailsService = cmsUserDetailsService;
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//
//
//    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.userDetailsService(this.cmsUserDetailsService);
//    }
//
//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//
//        super.configure(http);
//    }
}
