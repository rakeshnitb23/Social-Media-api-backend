package com.socialmedia.api.service;

import com.socialmedia.api.dto.StatsDto;
import com.socialmedia.api.repository.CommentRepository;
import com.socialmedia.api.repository.LikeRepository;
import com.socialmedia.api.repository.PostRepository;
import com.socialmedia.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    public StatsDto getSystemStats() {
        long userCount = userRepository.count();
        long postCount = postRepository.count();
        long likeCount = likeRepository.count();
        long commentCount = commentRepository.count();

        return StatsDto.builder()
                .userCount(userCount)
                .postCount(postCount)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .build();
    }
}
