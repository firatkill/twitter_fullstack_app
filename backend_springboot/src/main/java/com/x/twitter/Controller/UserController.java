package com.x.twitter.Controller;

import com.x.twitter.Model.DTO.UserProfile.UserProfileResponse;
import com.x.twitter.Service.Abstracts.FollowService;
import com.x.twitter.Service.Abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private  final FollowService followService;


    @GetMapping("/{username}")
    public UserProfileResponse getUserProfile(@PathVariable String username){
        return userService.getUserProfileByUsername(username);
    }
    @PostMapping("/{username}/follow")
    public void followUser(@PathVariable String username){
         followService.addFollow(username);
    }
    @PostMapping("/{username}/unfollow")
    public void unfollowUser(@PathVariable String username){
        followService.removeFollow(username);
    }
}
