package com.facebookclone.fb_backend.service;

import com.facebookclone.fb_backend.entity.Comment;
import com.facebookclone.fb_backend.entity.Post;
import com.facebookclone.fb_backend.entity.User;
import com.facebookclone.fb_backend.repository.CommentRepository;

import com.facebookclone.fb_backend.repository.PostRepository;
import com.facebookclone.fb_backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(
            CommentRepository commentRepository,
            PostRepository postRepository,
            UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));
        return commentRepository.findByPost(post);
    }

    public long getCommentsCount(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));
        return commentRepository.countByPost(post);
    }

    public Comment addComment(String content, Long postId, Long userId) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be empty");
        }
        if (content.length() > 1000) {
            throw new IllegalArgumentException("Comment content cannot exceed 1000 characters");
        }
        if (postId == null) {
            throw new IllegalArgumentException("Post ID cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUser(user);
        // createdAt sẽ được set bởi @PrePersist
        try {
            return commentRepository.save(comment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to save comment: " + e.getMessage(), e);
        }
    }
}