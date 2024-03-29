package com.x.twitter.Service.Concretes;

import com.x.twitter.Exceptions.TweetNotFoundException;
import com.x.twitter.Model.DTO.Auth.CreateUserRequest;
import com.x.twitter.Model.DTO.UserProfile.UserProfileResponse;
import com.x.twitter.Model.Entity.User;
import com.x.twitter.Model.Enum.Role;
import com.x.twitter.Repository.FollowRepository;
import com.x.twitter.Repository.UserRepository;
import com.x.twitter.Security.SecurityExceptions.UsernameAlreadyExistsException;
import com.x.twitter.Service.Abstracts.FollowService;
import com.x.twitter.Service.Abstracts.TweetService;
import com.x.twitter.Service.Abstracts.UserService;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FollowService followService;
    private final TweetService tweetService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Optional<User> user= userRepository.findByUsername(username);
       return user.orElseThrow( ()-> new UsernameNotFoundException("User not found"));
    }


    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional
    public void createUser(CreateUserRequest createUserRequest) throws UsernameAlreadyExistsException {
        if(userRepository.existsByUsername(createUserRequest.username())) throw new UsernameAlreadyExistsException("this username is already taken.");
        Set<Role> authorities=new HashSet<>();
        authorities.add(Role.ROLE_USER);

        User user= User.builder().name(createUserRequest.name()).biography(createUserRequest.biography()).password(passwordEncoder.encode(createUserRequest.password())).username(createUserRequest.username()).authorities(authorities).build();
        userRepository.save(user);

    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserProfileResponse getUserProfileByUsername(String username) {
       Optional<User> optUser= userRepository.findByUsername(username);
       if(optUser.isPresent()){
           User user=optUser.get();
           return new UserProfileResponse(user.getName(),user.getUsername(),user.getProfilePhoto(),user.getBiography(),user.getCreatedAt(), followService.getFollowersCountById(user.getId()), followService.getFollowingsCountById(user.getId()),tweetService.getAllTweetsByUserId(user.getId()) );
       }
       else throw new UsernameNotFoundException("user not found");

    }

}
