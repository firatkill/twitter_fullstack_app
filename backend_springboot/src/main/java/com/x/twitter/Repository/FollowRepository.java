package com.x.twitter.Repository;

import com.x.twitter.Model.Entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<FollowEntity,Long> {
    void deleteByFollowingUsername(String username);
    boolean existsByFollowingUsername(String username);
    long countByFollowing_Id(long followingId);
    long countByFollower_Id(long followerId);
}
