package com.x.twitter.Service.Concretes;

import com.x.twitter.Model.Entity.FollowEntity;
import com.x.twitter.Model.Entity.User;
import com.x.twitter.Repository.FollowRepository;
import com.x.twitter.Repository.UserRepository;
import com.x.twitter.Service.Abstracts.FollowService;
import com.x.twitter.Service.Abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    public void addFollow(String followingUsername) {
        FollowEntity followEntity=new FollowEntity();
        Optional<User> userToFollow= userRepository.findByUsername(followingUsername);

        followEntity.setFollower(getUser());
        followEntity.setFollowing(userToFollow.orElse(null));
        followRepository.save(followEntity);
    }

    @Override
    public void removeFollow(String followingUsername) {
        if(followRepository.existsByFollowingUsername(followingUsername)) followRepository.deleteByFollowingUsername(followingUsername);
    }

    @Override
    public long getFollowersCountById(long followingId) {
        return followRepository.countByFollowing_Id(followingId);
    }

    @Override
    public long getFollowingsCountById(long followerId) {
        return followRepository.countByFollower_Id(followerId);
    }
    private  User getUser(){
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
    }

}
