package com.facebookclone.fb_backend.controller;

import com.facebookclone.fb_backend.dto.Response;
import com.facebookclone.fb_backend.entity.Post;
import com.facebookclone.fb_backend.service.PostService;
import com.facebookclone.fb_backend.service.UserService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping(value = "/create-post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost(
            @RequestParam("content") String content,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "videoUrl", required = false) String videoUrl,
            @RequestParam("status") String status,
            @RequestParam("userId") Long userId) {
        try {
            // Validate userId
            if (userId == null) {
                throw new IllegalArgumentException("User ID không được để trống");
            }

            // Gọi service để tạo bài viết
            Post addedPost = postService.createPost(
                    content,
                    imageFile,
                    videoUrl, // Tên file (e.g., video1.mp4)
                    status,
                    userService.findById(userId)
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(addedPost);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi tạo bài viết: " + e.getMessage());
        }
    }

    @PutMapping("/update-post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestParam("content") String content,
                                                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                                                @RequestParam(value = "videoUrl", required = false) String videoUrl,
                                                                @RequestParam("status") String status,
                                                                @RequestParam(value = "videoFile", required = false) MultipartFile videoFile) {
        try{
            if(videoFile != null && !videoFile.isEmpty()) {
                Path targetDirectory = Paths.get("backend/Uploads").toAbsolutePath();

                try {
                    Files.createDirectories(targetDirectory);
                } catch (IOException e) {
                    // Ném lại exception nếu không tạo được thư mục
                    throw new IOException("Failed to create target directory: ", e);
                }

                String originalFilename = videoFile.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                // Tạo tên file duy nhất bằng UUID để tránh ghi đè file cũ nếu tên gốc trùng nhau
                String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
                Path targetFilePath = targetDirectory.resolve(uniqueFilename);
                try {
                    // Lấy InputStream từ MultipartFile
                    // Sao chép dữ liệu từ InputStream vào file tại targetFilePath
                    // StandardCopyOption.REPLACE_EXISTING (tùy chọn): Nếu file đã tồn tại, ghi đè lên.
                    // Với tên file duy nhất, tùy chọn này thường không cần thiết.
                    Files.copy(videoFile.getInputStream(), targetFilePath);
                    
                } catch (IOException e) {
                    // Xử lý lỗi lưu file: Ném lại exception để lớp gọi (Controller/Service khác) xử lý.
                    throw new IOException("Failed to save file: " + originalFilename, e);
                }
            }

            Post updatedPost = postService.updatePost(id, content, imageFile, videoUrl, status);
            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            e.printStackTrace(); // In chi tiết lỗi để debug
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi xảy ra khi sửa bài viết: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all-posts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }

    @GetMapping("/count-post")
    public Long countPost() {
        return postService.countPost();
    }
    
}