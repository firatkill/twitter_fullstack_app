package com.x.twitter.Service.Abstracts;


import com.x.twitter.Model.DTO.Auth.CreateUserRequest;
import com.x.twitter.Model.DTO.UserProfile.UserProfileResponse;
import com.x.twitter.Model.Entity.User;
import com.x.twitter.Security.SecurityExceptions.UsernameAlreadyExistsException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    boolean existsByUsername(String username);

    void createUser(CreateUserRequest createUserRequest) throws UsernameAlreadyExistsException;
    User findByUsername(String username);

    UserProfileResponse getUserProfileByUsername(String username);
}
