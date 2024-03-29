package com.x.twitter.Service.Abstracts;

import com.x.twitter.Exceptions.TweetNotFoundException;
import com.x.twitter.Model.DTO.Tweets.TweetRequest;
import com.x.twitter.Model.DTO.Tweets.TweetResponse;
import com.x.twitter.Model.Entity.TweetEntity;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface TweetService {
    TweetResponse getTweetById(long tweetId);
    List<TweetResponse> getAllTweets();
    List<TweetResponse> getAllTweetsByUserId(long userId);
    void createTweet(TweetRequest tweetRequest) throws BadRequestException;
    void deleteTweetById(long tweetId) throws TweetNotFoundException;
    List<TweetResponse> getRepliesByTweetId(long tweetId);

    TweetEntity mapFromRequest(TweetRequest tweetRequest) throws BadRequestException;
    TweetResponse mapToResponse(TweetEntity tweetEntity);
}
