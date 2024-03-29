package com.x.twitter.Security.Filter;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.twitter.Model.DTO.Auth.Authrequest;
import com.x.twitter.Security.JWT.JWTService;
import com.x.twitter.Service.Abstracts.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;

@Component

@Slf4j
public class CustomUPFilter extends AbstractAuthenticationProcessingFilter {

    private final  AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;
    protected static String requestBody=null;


    public CustomUPFilter(AuthenticationManager authenticationManager, JWTService jwtService, UserService userService) {
        super("/api/auth/login");
        this.authenticationManager=authenticationManager;
        this.jwtService = jwtService;


        this.userService = userService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
    requestBody = request.getReader().lines().collect(Collectors.joining());


        ObjectMapper objectMapper= new ObjectMapper();
        Authrequest authRequest =objectMapper.readValue(requestBody, Authrequest.class);
        String username=authRequest.username();
        String password=authRequest.password();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        var user=  userService.loadUserByUsername((String) authResult.getPrincipal());
        Algorithm algorithm=Algorithm.HMAC256("secret".getBytes());

        String access_token= jwtService.generateToken(user);


        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // response.addHeader...
        response.getWriter().format("""
                        {
                          "user":[
                            {
                              "username":"%s",
                              "access_token":"%s"
                            }
                          ]
                        }
                        """,
                user.getUsername(),
                access_token
        );

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // response.addHeader...
        response.getWriter().format("""
                        {
                          "error":[
                            {
                              "status": %d,
                              "detail": "%s"
                            }
                          ]
                        
                        """,
                401,
                failed.getMessage()
        );
        response.setStatus(401);

    }
    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(this.authenticationManager);
    }
}
