package com.socialmedia.api.repository;

import com.socialmedia.api.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerIdAndFollowedId(Long followerId, Long followedId);

    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);

    long countByFollowerId(Long followerId);

    long countByFollowedId(Long followedId);

    void deleteByFollowerIdAndFollowedId(Long followerId, Long followedId);

    @Query("SELECT f.followed.id FROM Follow f WHERE f.follower.id = :userId")
    List<Long> findFollowingIds(Long userId);

    @Query("SELECT f.follower.id FROM Follow f WHERE f.followed.id = :userId")
    List<Long> findFollowerIds(Long userId);
}
