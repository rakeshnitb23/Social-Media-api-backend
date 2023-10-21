package com.socialmedia.api.repository;

import com.socialmedia.api.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    long countByPostId(Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.user.id = :userId")
    long countTotalLikesReceivedByUser(Long userId);
}
