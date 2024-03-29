package com.x.twitter.Security.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.x.twitter.Model.DTO.Auth.CreateUserRequest;
import com.x.twitter.Security.JWT.JWTService;
import com.x.twitter.Service.Abstracts.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component
@RequiredArgsConstructor
public class CustomOPRFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if(request.getServletPath().equals("/api/auth/login")){
            filterChain.doFilter(request,response);
        } else if (request.getServletPath().equals("/api/auth/register")) {
            var reader=request.getInputStream();
            byte[] requestBody =reader.readAllBytes();

            String str = new String(requestBody, StandardCharsets.UTF_8);
            ObjectMapper objectMapper= new ObjectMapper();
            var createUserRequest = objectMapper.readValue(requestBody, CreateUserRequest.class);
            if(userService.existsByUsername(createUserRequest.username())){
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(409);
                response.getWriter().format("""
                        {
                          "error":[
                            {
                              "status": %d,
                              "detail": "%s"
                            }
                          ]
                        }
                        """,409,"User Already Exists");


            }else{

                userService.createUser(createUserRequest);
            }


        } else{

                 try{

            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUser(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
                UserDetails user = userService.loadUserByUsername(username);
                if (jwtService.validateToken(token, user)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    response.setHeader("access_token",jwtService.generateToken(user));

                }
                    }
                 }catch (Exception e){

                     response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                     response.setStatus(409);
                     response.getWriter().format("""
                        {
                          "error":[
                            {
                              "status": %d,
                              "detail": "%s"
                            }
                          ]
                        }
                        """,409,"Invalid Token");

                 }

                filterChain.doFilter(request, response);

            }

    }


}



