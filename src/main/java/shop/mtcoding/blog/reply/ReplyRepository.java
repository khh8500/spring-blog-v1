package shop.mtcoding.blog.reply;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class ReplyRepository {
    private final EntityManager em;

    @Transactional
    public void deleteById(int id) {
        String q = "delete from reply_tb where id = ?";
        Query query = em.createNativeQuery(q);
        query.setParameter(1, id);

        query.executeUpdate();
    }

    public Reply findById(int id) {
        String q = "select * from reply_tb where id = ?";
        Query query = em.createNativeQuery(q, Reply.class);
        query.setParameter(1, id);

        try {
            return (Reply) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void save(ReplyRequest.WriteDTO requestDTO, int userId) {
        Query query = em.createNativeQuery("insert into reply_tb(comment, board_id, user_id, created_at) values(?,?,?, now())");
        query.setParameter(1, requestDTO.getComment());
        query.setParameter(2, requestDTO.getBoardId());
        query.setParameter(3, userId);

        query.executeUpdate();
    }
}