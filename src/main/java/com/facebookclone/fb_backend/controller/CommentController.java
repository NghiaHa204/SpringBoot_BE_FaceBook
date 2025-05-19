package com.facebookclone.fb_backend.controller;

import com.facebookclone.fb_backend.dto.Comment.CommentRequest;
import com.facebookclone.fb_backend.dto.Comment.CommentResponse;
import com.facebookclone.fb_backend.entity.Comment;
import com.facebookclone.fb_backend.entity.User;
import com.facebookclone.fb_backend.repository.CommentRepository;
import com.facebookclone.fb_backend.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<CommentResponse> getCommentsCount(@PathVariable Long postId) {
        long count = commentService.getCommentsCount(postId);
        return ResponseEntity.ok(new CommentResponse("commentsCount", count));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@Valid @RequestBody CommentRequest request) {
        Comment savedComment = commentService.addComment(
                request.getContent(),
                request.getPostId(),
                request.getUserId()
        );
        return ResponseEntity.ok(new CommentResponse("success", true));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommentResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new CommentResponse("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommentResponse> handleGeneralException(Exception ex) {
        return ResponseEntity.status(500).body(new CommentResponse("error", "Internal server error: " + ex.getMessage()));
    }

}