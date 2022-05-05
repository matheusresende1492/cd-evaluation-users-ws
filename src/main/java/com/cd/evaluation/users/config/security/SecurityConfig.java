package com.cd.evaluation.users.config.security;

import com.cd.evaluation.users.config.security.filter.CustomAuthenticationFilter;
import com.cd.evaluation.users.config.security.filter.CustomAuthorizationFilter;
import com.cd.evaluation.users.config.security.filter.algorithm.AlgorithmManager;
import com.cd.evaluation.users.model.enums.roles.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AlgorithmManager algorithmManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.GET).hasAnyAuthority(RoleEnum.ROLE_ADMIN.toString(), RoleEnum.ROLE_USER.toString());
        http.authorizeRequests().antMatchers(HttpMethod.POST).hasAnyAuthority(RoleEnum.ROLE_ADMIN.toString());
        http.authorizeRequests().antMatchers(HttpMethod.PUT).hasAnyAuthority(RoleEnum.ROLE_ADMIN.toString());
        http.authorizeRequests().antMatchers(HttpMethod.DELETE).hasAnyAuthority(RoleEnum.ROLE_ADMIN.toString());
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManager(), algorithmManager));
        http.addFilterBefore(new CustomAuthorizationFilter(algorithmManager), UsernamePasswordAuthenticationFilter.class);
    }
}
