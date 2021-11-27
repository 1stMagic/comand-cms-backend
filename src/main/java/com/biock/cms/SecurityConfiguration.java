package com.biock.cms;

import com.biock.cms.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    public SecurityConfiguration(final JwtFilter jwtFilter) {

        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/backend/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);
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
