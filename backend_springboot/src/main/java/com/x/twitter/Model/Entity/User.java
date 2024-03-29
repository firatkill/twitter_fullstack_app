package com.x.twitter.Model.Entity;

import com.x.twitter.Model.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Blob;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false,unique = true)
    private String username;

    @Lob
    @Column(name = "profile_photo")
    private byte[] profilePhoto;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String biography;

    @Column(nullable = false)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> authorities = new HashSet<>();

    @CreationTimestamp
    private Instant createdAt;


    @Column( columnDefinition = "tinyint(1) DEFAULT '1'")
    private boolean accountNonExpired;
    @Column( columnDefinition = "tinyint(1) DEFAULT '1'")
    private boolean isEnabled;
    @Column( columnDefinition = "tinyint(1) DEFAULT '1'")
    private boolean accountNonLocked;
    @Column( columnDefinition = "tinyint(1) DEFAULT '1'")
    private boolean credentialsNonExpired;







}
