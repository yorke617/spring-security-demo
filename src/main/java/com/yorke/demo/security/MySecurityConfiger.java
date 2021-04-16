package com.objectiva.demo.security;

import com.objectiva.demo.handlers.MyAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Slf4j
@Configuration
@EnableWebSecurity
public class MySecurityConfiger extends WebSecurityConfigurerAdapter {

    @Resource(name = "userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private AuthenticationFailedEntryPoint authenticationFailedEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Configure the http request for spring security.");
        http
                .formLogin() //Start to open security default login configuration.
                .loginPage("/login.html") //Set the default login page
                .loginProcessingUrl("/login") //Set the login request URL
                .successForwardUrl("/login/successful") //Set the forward URL after login successful.
                .failureForwardUrl("/login/failed"); //Set the forward URL after login failed.

        http
                .cors() //Cross Origin Resourse-Sharing
                .and()
                .csrf().disable() //Close the csrf protect.
                .authorizeRequests() //Open authentication
                .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/webSocket/**").permitAll() //Open some static resources
                .anyRequest().authenticated() //Match all request
                .and()
                .exceptionHandling() //Open exception handler
                .authenticationEntryPoint(authenticationFailedEntryPoint)//Solve the exception when the anonymous access the resource.
                .accessDeniedHandler(myAccessDeniedHandler);//Solve the exception when the user had verified accessed the resource without authority.
    }
}
