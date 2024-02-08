package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "board_tb")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sno;

    @Column(length = 20)
    private String title;
    @Column(length = 20)
    private String content;

    private String author;
}
