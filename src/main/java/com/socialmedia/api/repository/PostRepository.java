package com.socialmedia.api.repository;

import com.socialmedia.api.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id IN " +
            "(SELECT f.followed.id FROM Follow f WHERE f.follower.id = :userId) " +
            "ORDER BY p.createdAt DESC")
    Page<Post> findFeedPostsByUserId(Long userId, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.user.id = :userId")
    long countByUserId(Long userId);

    @Query(value = "SELECT * FROM posts ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<Post> findLatestPosts(int limit);
}
