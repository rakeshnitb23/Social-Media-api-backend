package com.socialmedia.api.service;

import com.socialmedia.api.dto.PostCreateRequest;
import com.socialmedia.api.dto.PostDto;
import com.socialmedia.api.dto.PostResponse;
import com.socialmedia.api.exception.ResourceNotFoundException;
import com.socialmedia.api.model.Post;
import com.socialmedia.api.model.User;
import com.socialmedia.api.repository.LikeRepository;
import com.socialmedia.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final LikeRepository likeRepository;

    @Transactional
    public PostDto createPost(PostCreateRequest request, Long userId) {
        User user = userService.getUserById(userId);

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);
        return mapToDto(savedPost);
    }

    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return mapToDto(post);
    }

    public PostResponse getUserPosts(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> postPage = postRepository.findByUserId(userId, pageable);

        return buildPostResponse(postPage);
    }

    public PostResponse getFeedPosts(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findFeedPostsByUserId(userId, pageable);

        return buildPostResponse(postPage);
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalStateException("User is not authorized to delete this post");
        }

        postRepository.delete(post);
    }

    private PostDto mapToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorId(post.getUser().getId())
                .authorUsername(post.getUser().getUsername())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    private PostResponse buildPostResponse(Page<Post> postPage) {
        return PostResponse.builder()
                .content(postPage.getContent().stream().map(this::mapToDto).toList())
                .pageNo(postPage.getNumber())
                .pageSize(postPage.getSize())
                .totalElements(postPage.getTotalElements())
                .totalPages(postPage.getTotalPages())
                .last(postPage.isLast())
                .build();
    }
}
