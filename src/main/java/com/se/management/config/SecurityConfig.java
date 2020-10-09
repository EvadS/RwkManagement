package com.se.management.config;


import com.se.management.config.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private  JwtTokenProvider tokenProperties;

    @Autowired

    private  BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private  CustomUserDetailsService userDetailsService;



    @Autowired
    private  RwrAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private  JwtTokenProvider tokenProvider;

    @Autowired
    private  JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;



    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)

                .and()
                .logout()
                .and()



                .addFilter(new JwtTokenAuthorizationFilter(authenticationManagerBean(), tokenProvider, tokenProperties))

                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/register").permitAll()

                .antMatchers("/api/users/**").hasRole("ADMIN")
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/demo/",
                        "/communication/",
                        "/info/",
                        "/images/*",
                        "/images//",
                        "/webjars/**")
                .permitAll()
                .antMatchers("/**/api/auth/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll();

           httpSecurity.addFilterBefore(jwtTokenAuthenticationFilter,
                   UsernamePasswordAuthenticationFilter.class);
    }
}