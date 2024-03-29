package com.x.twitter.Model.DTO.Tweets;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.x.twitter.Model.DTO.Users.UserDTO;

import java.time.Instant;
import java.util.Date;

public record TweetResponse(long id, String content, Instant createdAt, long likeCount, UserDTO user,long replyTo) {
}
