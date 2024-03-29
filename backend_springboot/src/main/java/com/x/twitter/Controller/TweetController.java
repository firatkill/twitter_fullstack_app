package com.x.twitter.Controller;

import com.x.twitter.Exceptions.TweetNotFoundException;
import com.x.twitter.Model.DTO.Tweets.TweetRequest;
import com.x.twitter.Model.DTO.Tweets.TweetResponse;
import com.x.twitter.Model.Entity.TweetEntity;
import com.x.twitter.Service.Abstracts.LikeService;
import com.x.twitter.Service.Abstracts.TweetService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/tweets")
public class TweetController {
    private final TweetService tweetService;
    private final LikeService likeService;

    @GetMapping("/{todoId}")
    public TweetResponse getOneTweet(@PathVariable long todoId){
        return tweetService.getTweetById(todoId);
    }
    @PostMapping("")
    public void postTweet(@RequestBody TweetRequest tweetRequest) throws BadRequestException {
        tweetService.createTweet(tweetRequest);
    }
    @GetMapping()
    public List<TweetResponse> getAllTweets(){
        return tweetService.getAllTweets();
    }
    @DeleteMapping("/{tweetId}")
    public void deleteTweet(@PathVariable long tweetId) throws TweetNotFoundException {
        tweetService.deleteTweetById(tweetId);
    }

    @PostMapping("/{tweetId}/add-like")
    public void addLikeToTweet(@PathVariable long tweetId){
        likeService.addLikeByTweetId(tweetId);
    }
    @PostMapping("/{tweetId}/remove-like")
    public void removeLikeToTweet(@PathVariable long tweetId){
        likeService.removeLikeByTweetId(tweetId);
    }

}
