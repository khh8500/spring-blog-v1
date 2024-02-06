package shop.mtcoding.blog.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity // 리플렉션
@Table(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;

    @Column(length = 60, nullable = false) // null일 수 없다
    private String password;
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
