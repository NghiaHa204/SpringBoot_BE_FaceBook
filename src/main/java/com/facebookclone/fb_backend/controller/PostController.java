package com.facebookclone.fb_backend.controller;

import com.facebookclone.fb_backend.entity.Post;
import com.facebookclone.fb_backend.service.PostService;
import com.facebookclone.fb_backend.service.UserService;

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



@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private static final String UPLOAD_DIR_PHYSICAL = "C:\\Users\\Lenovo\\OneDrive\\Documents\\CloneFacebook - Copy\\Facebook-Clone\\backend\\Uploads";
    private static final String UPLOAD_URL_PATH = "/Uploads/";


    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

     @PostMapping(value = "/createPost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost(@RequestParam("content") String content,
                                        @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                        // videoUrl is now primarily for external URLs if no videoFile is uploaded
                                        // videoUrl giờ chủ yếu dùng cho các URL bên ngoài nếu không upload videoFile
                                        @RequestParam(value = "videoUrl", required = false) String videoUrl,
                                        @RequestParam("status") String status,
                                        @RequestParam(value = "videoFile", required = false) MultipartFile videoFile) {
        try {
            // === Bổ sung: Biến để lưu URL video cuối cùng sẽ dùng ===
            // Khởi tạo bằng giá trị videoUrl nhận từ request (dùng cho URL ngoài nếu không upload file)
            String finalVideoUrl = videoUrl;
            // ======================================================

            // --- Handle video file upload if present (Logic gốc của bạn) ---
            // Xử lý upload file video nếu có (Logic gốc của bạn)
            if(videoFile != null && !videoFile.isEmpty()) {
                // Sử dụng hằng số đường dẫn vật lý đã định nghĩa
                Path targetDirectory = Paths.get(UPLOAD_DIR_PHYSICAL).toAbsolutePath(); // Sử dụng hằng số đường dẫn vật lý

                try {
                    Files.createDirectories(targetDirectory); // Đảm bảo thư mục tồn tại
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
                    // === Dòng này là để lưu file vật lý (Logic gốc của bạn) ===
                    Files.copy(videoFile.getInputStream(), targetFilePath);
                    // ======================================================

                    // === Bổ sung: Tạo URL HTTP công khai và gán cho finalVideoUrl ===
                    // Combine the base URL (e.g., http://localhost:8080) with the configured URL path and filename
                    // Kết hợp base URL (ví dụ: http://localhost:8080) với đường dẫn URL đã cấu hình và tên file
                    // IMPORTANT: In production, replace "http://localhost:8080" with your actual domain/base URL
                    // QUAN TRỌNG: Trong môi trường production, thay thế "http://localhost:8080" bằng domain/base URL thực tế của bạn
                    finalVideoUrl = "http://localhost:8080" + UPLOAD_URL_PATH + uniqueFilename; // <-- Cập nhật finalVideoUrl bằng URL HTTP
                    // ==============================================================

                } catch (IOException e) {
                    // Xử lý lỗi lưu file: Ném lại exception để lớp gọi (Controller/Service khác) xử lý.
                    throw new IOException("Failed to save file: " + originalFilename, e);
                }
            }
            // --- End handle video file upload ---


            // Call the service layer to create the post, passing the final video URL
            // Gọi tầng service để tạo bài viết, truyền URL video cuối cùng
            // === Sử dụng finalVideoUrl đã được cập nhật ===
            Post addedPost = postService.createPost(content, imageFile, finalVideoUrl, status, userService.findById(Long.valueOf(1))); // Assuming user ID 1 for now
            // ============================================
            // Giả định user ID là 1 tạm thời

            return ResponseEntity.status(HttpStatus.CREATED).body(addedPost);

        } catch (Exception e) {
            e.printStackTrace(); // In chi tiết lỗi để debug
            // In chi tiết lỗi để debug
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi xảy ra khi đăng bài viết: " + e.getMessage());
        }
    }

    @PutMapping("/updatePost/{id}")
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

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllPosts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }

    @GetMapping("/countPost")
    public Long countPost() {
        return postService.countPost();
    }
    
}