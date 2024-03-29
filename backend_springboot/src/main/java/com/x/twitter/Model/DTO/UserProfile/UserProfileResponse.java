package com.x.twitter.Model.DTO.UserProfile;

import com.x.twitter.Model.DTO.Tweets.TweetResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private String name;
    private String username;
    private byte[] profilePhoto;
    private String biography;
    private Instant createdAt;
    private Long followerCount;
    private Long followedCount;
    private List<TweetResponse> tweets;


}
