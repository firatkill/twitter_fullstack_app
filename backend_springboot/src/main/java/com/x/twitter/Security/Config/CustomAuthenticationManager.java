package com.x.twitter.Security.Config;

import com.x.twitter.Model.Entity.User;
import com.x.twitter.Model.Enum.Role;
import com.x.twitter.Service.Abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {


    private final UserService userService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = (User) userService.loadUserByUsername((String) authentication.getPrincipal());

        if (user!=null) {
            if (passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
                List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
                for (Role role : user.getAuthorities()) {
                    grantedAuthorityList.add(new SimpleGrantedAuthority(role.getAuthority()));
                }

                return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorityList);
            } else {

                throw new BadCredentialsException("Wrong Password");

            }
        } else {

            throw new BadCredentialsException("User not found in database.");
        }
    }
}