package com.socialmedia.api.controller;

import com.socialmedia.api.dto.PostCreateRequest;
import com.socialmedia.api.dto.PostDto;
import com.socialmedia.api.dto.PostResponse;
import com.socialmedia.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody PostCreateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        return new ResponseEntity<>(postService.createPost(request, userId), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PostResponse> getUserPosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.getUserPosts(userId, page, size));
    }

    @GetMapping("/feed")
    public ResponseEntity<PostResponse> getFeedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        return ResponseEntity.ok(postService.getFeedPosts(userId, page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        postService.deletePost(id, userId);
        return ResponseEntity.noContent().build();
    }

    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        return ((com.socialmedia.api.model.User) userDetails).getId();
    }
}
