package com.x.twitter.Repository;

import com.x.twitter.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>  findByUsername(String username);
    boolean existsByUsername(String username);



}
