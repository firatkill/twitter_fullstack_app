package com.x.twitter.Service.Concretes;

import com.x.twitter.Exceptions.TweetNotFoundException;
import com.x.twitter.Model.DTO.Tweets.TweetRequest;
import com.x.twitter.Model.DTO.Tweets.TweetResponse;
import com.x.twitter.Model.DTO.Users.UserDTO;
import com.x.twitter.Model.Entity.TweetEntity;
import com.x.twitter.Model.Entity.User;
import com.x.twitter.Repository.TweetRepository;
import com.x.twitter.Repository.UserRepository;
import com.x.twitter.Service.Abstracts.TweetService;
import com.x.twitter.Service.Abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Override
    public TweetResponse getTweetById(long tweetId) {
        Optional<TweetEntity> tweetEntity=tweetRepository.findById(tweetId);
        return tweetEntity.map(this::mapToResponse).orElse(null);
    }

    @Override

    public List<TweetResponse> getAllTweets() {
        List<TweetEntity> tweetList=tweetRepository.findAllByUser_Id(getUser().getId());
        List<TweetResponse> tweetResponseList= new ArrayList<>();
        tweetList.forEach(tweet->tweetResponseList.add(mapToResponse(tweet)));
        return tweetResponseList;
    }
    @Override
    @Transactional
    public List<TweetResponse> getAllTweetsByUserId(long userId) {
        List<TweetEntity> tweetList=tweetRepository.findAllByUser_Id(userId);
        List<TweetResponse> tweetResponseList= new ArrayList<>();
        tweetList.forEach(tweet->tweetResponseList.add(mapToResponse(tweet)));
        return tweetResponseList;
    }



    @Override
    @Transactional
    public void createTweet(TweetRequest tweetRequest) throws BadRequestException {
        tweetRepository.save(mapFromRequest(tweetRequest));

    }

    @Override
    @Transactional
    public void deleteTweetById(long tweetId) throws TweetNotFoundException {
        if(tweetRepository.existsById(tweetId))
            tweetRepository.deleteById(tweetId);
        else throw new TweetNotFoundException("Tweet not found");


    }

    @Override
    public List<TweetResponse> getRepliesByTweetId(long tweetId) {
        List<TweetEntity> tweetList=tweetRepository.findAllByReplyTo(tweetId);
        List<TweetResponse> responseList=new ArrayList<>();
        tweetList.forEach(tweet->responseList.add(mapToResponse(tweet)));
        return responseList;
    }

    @Override
    public TweetEntity mapFromRequest(TweetRequest tweetRequest) throws BadRequestException {

        TweetEntity tweetEntity=new TweetEntity();
        tweetEntity.setContent(tweetRequest.content());
        if(tweetRequest.id()!=null) tweetEntity.setId(tweetRequest.id());
        tweetEntity.setUser(getUser());
        System.out.println(tweetRequest);
        tweetEntity.setLikeCount(tweetRequest.likeCount());
        if(tweetRequest.replyTo()!=0  && tweetRepository.existsById(tweetRequest.replyTo()) ) tweetEntity.setReplyTo(tweetRequest.replyTo());
        if(tweetRequest.replyTo() != 0 && !tweetRepository.existsById(tweetRequest.replyTo())) throw new BadRequestException("No such tweet found. Invalid reply Id");
        return tweetEntity;
    }
    @Override
    public TweetResponse mapToResponse(TweetEntity tweetEntity) {
        UserDTO userDTO=new UserDTO(tweetEntity.getUser().getId(), tweetEntity.getUser().getUsername());

        return new TweetResponse(tweetEntity.getId(),tweetEntity.getContent(),tweetEntity.getCreatedAt(),tweetEntity.getLikeCount(),userDTO, tweetEntity.getReplyTo());
    }

    private User getUser(){
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
    }
}
