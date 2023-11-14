package com.socialmedia.api.service;

import com.socialmedia.api.exception.AlreadyLikedException;
import com.socialmedia.api.exception.ResourceNotFoundException;
import com.socialmedia.api.model.Like;
import com.socialmedia.api.model.Post;
import com.socialmedia.api.model.User;
import com.socialmedia.api.repository.LikeRepository;
import com.socialmedia.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public void likePost(Long postId, Long userId) {
        // Check if post exists
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        // Check if user already liked the post
        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new AlreadyLikedException("User already liked this post");
        }

        // Get user
        User user = userService.getUserById(userId);

        // Create and save like
        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        likeRepository.save(like);
    }

    @Transactional
    public void unlikePost(Long postId, Long userId) {
        // Check if post exists
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        // Delete like if it exists
        Like like = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Like not found"));

        likeRepository.delete(like);
    }

    public boolean hasUserLikedPost(Long postId, Long userId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }

    public long countLikesByPostId(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    public long countTotalLikesReceivedByUser(Long userId) {
        return likeRepository.countTotalLikesReceivedByUser(userId);
    }
}
