package com.x.twitter.Service.Abstracts;

import com.x.twitter.Model.Entity.TweetEntity;

public interface LikeService {
    void addLikeByTweetId(long id);
    void removeLikeByTweetId(long id);
}
