package com.facebookclone.fb_backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration // Đánh dấu đây là lớp cấu hình Spring
public class StaticResourceConfig implements WebMvcConfigurer {

    // Định nghĩa thư mục gốc nơi lưu trữ các file upload
    // Sử dụng Paths.get().toAbsolutePath() để đảm bảo đường dẫn tuyệt đối
    // === THAY ĐỔI ĐƯỜNG DẪN NÀY THÀNH THƯ MỤC THỰC TẾ CỦA BẠN ===
    // Ví dụ: Paths.get("C:\\Users\\Lenovo\\OneDrive\\Documents\\Facebook-Clone\\backend\\Uploads")
    // hoặc Paths.get("/path/to/your/project/backend/Uploads") trên Linux/macOS
    // Sử dụng Paths.get("backend", "Uploads").toAbsolutePath() có thể hoạt động nếu bạn luôn chạy ứng dụng từ thư mục gốc dự án, nhưng đường dẫn tuyệt đối tường minh an toàn hơn.
    private static final String UPLOAD_DIR_PHYSICAL = Paths.get("Uploads").toAbsolutePath().toString();


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ánh xạ đường dẫn URL "/Uploads/**" tới thư mục vật lý
        // Khi có yêu cầu đến "/Uploads/ten-file.mp4", Spring sẽ tìm file đó trong thư mục UPLOAD_DIR_PHYSICAL
        registry.addResourceHandler("/Uploads/**")
                // === SỬA LỖI: Sử dụng "file:" prefix và đường dẫn tuyệt đối ===
                // Chuyển đổi đường dẫn vật lý thành URI để đảm bảo hoạt động đúng trên các hệ điều hành khác nhau
                .addResourceLocations("file:" + Paths.get(UPLOAD_DIR_PHYSICAL).toUri().getRawPath());

        // Ví dụ thêm: Cấu hình phục vụ các tài nguyên tĩnh mặc định của Spring (như từ src/main/resources/static)
        // registry.addResourceHandler("/**")
        //         .addResourceLocations("classpath:/static/");
    }
}
