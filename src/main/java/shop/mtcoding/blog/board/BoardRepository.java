package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public List<Board> findAll(){
        Query query = em.createNativeQuery("select * from board_tb order by sno desc", Board.class);
        return query.getResultList();
    }

    public Board findBySno(int sno) {
        Query query = em.createNativeQuery("select * from board_tb where sno = ?", Board.class);
        query.setParameter(1, sno);

        Board board = (Board) query.getSingleResult();
        return board;
    }
    @Transactional
    public void save(BoardRequest.SaveDTO requestDTO) {
        Query query = em.createNativeQuery("insert into board_tb(author, title, content) values (?,?,?)");
        query.setParameter(1, requestDTO.getAuthor());
        query.setParameter(2, requestDTO.getTitle());
        query.setParameter(3, requestDTO.getContent());

        query.executeUpdate();
    }

    @Transactional
    public void delete(int sno) {
        Query query = em.createNativeQuery("delete from board_tb where sno = ?");
        query.setParameter(1, sno);
        query.executeUpdate();
    }

    @Transactional
    public void update(BoardRequest.UpdateDTO requestDTO, int sno) {
        Query query = em.createNativeQuery("update board_tb set author = ?, title = ?, content = ? where sno = ?");
        query.setParameter(1, requestDTO.getAuthor());
        query.setParameter(2, requestDTO.getTitle());
        query.setParameter(3, requestDTO.getContent());
        query.setParameter(4, sno);

        query.executeUpdate();
    }

}
