package shop.mtcoding.blog.board;

import lombok.Data;

public class BoardRequest {

    @Data
    public static class SaveDTO {
        private String title;
        private String content;
    }

    @Data
    public static class UpdateDTO { // 같은 내용이더라도 유효성 검사가 다를 수 있음
        private String title;
        private String content;
    }
}