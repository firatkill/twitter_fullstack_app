package com.x.twitter.Repository;

import com.x.twitter.Model.DTO.Tweets.TweetResponse;
import com.x.twitter.Model.Entity.TweetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<TweetEntity,Long> {
    List<TweetEntity> findAllByUser_Id(long userId);
    List<TweetEntity> findAllByReplyTo(long tweetId);
}
