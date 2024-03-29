package com.x.twitter.Service.Abstracts;

import com.x.twitter.Model.Entity.FollowEntity;

public interface FollowService {
    void addFollow(String followingUsername);
    void removeFollow(String followingUsername);
    long getFollowersCountById(long followingId);
    long getFollowingsCountById(long followerId);
}
