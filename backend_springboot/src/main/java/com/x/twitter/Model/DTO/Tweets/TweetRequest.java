package com.x.twitter.Model.DTO.Tweets;

import com.x.twitter.Model.DTO.Users.UserDTO;


import java.util.Date;

public record   TweetRequest(Long id, String content, UserDTO user, Date createdAt, long likeCount,long replyTo) {

}
