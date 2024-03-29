package com.x.twitter.Service.Concretes;

import com.x.twitter.Model.Entity.LikeEntity;
import com.x.twitter.Model.Entity.TweetEntity;
import com.x.twitter.Model.Entity.User;
import com.x.twitter.Repository.LikeRepository;
import com.x.twitter.Repository.TweetRepository;
import com.x.twitter.Repository.UserRepository;
import com.x.twitter.Service.Abstracts.LikeService;
import com.x.twitter.Service.Abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.Like;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    @Override
    public void addLikeByTweetId(long id) {
        Optional<TweetEntity> tweet=tweetRepository.findById(id);

        if(tweet.isPresent()){
            TweetEntity tweetToUpdate=tweet.get();
            tweetToUpdate.setLikeCount(tweet.get().getLikeCount()+1);

            LikeEntity like=new LikeEntity();
            like.setTweet(tweetToUpdate);
            like.setTweet(tweetToUpdate);
            like.setUser(getUser());
            likeRepository.save(like);
            tweetRepository.save(tweetToUpdate);
        }
    }

    @Override
    public void removeLikeByTweetId(long id) {
        Optional<TweetEntity> tweet=tweetRepository.findById(id);
        Optional<LikeEntity> like=likeRepository.findByUserIdAndTweetId(getUser().getId(),tweet.get().getId());
        if(like.isPresent()){
            TweetEntity tweetToupdate=tweet.get();
            tweetToupdate.setLikeCount(tweet.get().getLikeCount()-1);
            likeRepository.delete(like.get());
            tweetRepository.save(tweetToupdate);
        }
    }
    private  User getUser(){
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
    }
}
