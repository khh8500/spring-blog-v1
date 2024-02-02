package shop.mtcoding.blog.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

public class BoardResponse {

    // bt.id, bt.title, bt.content, bt.created_at, bt.user_id, ut.username

    @AllArgsConstructor
    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String content;
        private Timestamp createdAt;
        private Integer userId; // 작성자 ID
        private String username;
    }
}
