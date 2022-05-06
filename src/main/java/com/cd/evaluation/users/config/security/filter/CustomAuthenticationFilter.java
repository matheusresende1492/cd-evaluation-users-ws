package com.cd.evaluation.users.config.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cd.evaluation.users.config.security.filter.algorithm.AlgorithmManager;
import com.cd.evaluation.users.exception.InternalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Authentication Filter Class
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String CREDENTIALS = "credentials";

    private final AuthenticationManager authenticationManager;

    private final AlgorithmManager algorithmManager;

    public CustomAuthenticationFilter(
            AuthenticationManager authenticationManager,
            AlgorithmManager algorithmManager
    ){
        this.authenticationManager = authenticationManager;
        this.algorithmManager = algorithmManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //Getting credentials (email and password in Base64)
        String credentials = request.getParameter(CREDENTIALS);
        if (StringUtils.isBlank(credentials)) {
            log.error("Error null or empty credentials.");
            throw new InternalException("authorization.cannot.be.null.or.empty", HttpStatus.BAD_REQUEST);
        }
        byte[] decodedCredentials = Base64.getDecoder().decode(credentials);
        List<String> separatedCredentials = List.of(new String(decodedCredentials).split("&"));
        if (separatedCredentials.size() != 2) {
            log.error("Number of arguments invalid. Expected: 2");
            throw new InternalException("credentials.number.of.arguments.invalid", HttpStatus.BAD_REQUEST);
        }
        String email = separatedCredentials.get(0);
        String password = separatedCredentials.get(1);
        log.info("Attempt login. Email: {}", email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    //In case of a successful login attempt, generate the user JWT token
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, InternalException {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = algorithmManager.retrieveAlgorithm();
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                //Adding 20 minutes expiration time to the access token
                .withExpiresAt(new Date(System.currentTimeMillis() + 20 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                //Adding 1 hour expiration time to the refresh token
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        //Making a response object
        Map<String, String> tokensHash = new HashMap<>();
        tokensHash.put("access_token", access_token);
        tokensHash.put("refresh_token", refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokensHash);
    }
}
