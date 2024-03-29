package com.x.twitter.Repository;

import com.x.twitter.Model.Entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.Like;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity,Long> {
    Optional<LikeEntity> findByUserIdAndTweetId(long userId, long tweetId);
}
