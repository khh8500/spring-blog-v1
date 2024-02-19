package shop.mtcoding.blog.board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public Long count() {
        Query query = em.createNativeQuery("select count(*) from board_tb");
        return (Long) query.getSingleResult();
    }

    public Long count(String keyword) {
        Query query = em.createNativeQuery("select count(*) from board_tb where title like ?");
        query.setParameter(1, "%" + keyword + "%");
        return (Long) query.getSingleResult();
    }

    public List<Board> findAll(Integer page, String keyword) {
        Query query = em.createNativeQuery("select * from board_tb where title like ? order by id desc limit ?, 3", Board.class);
        query.setParameter(1, "%" + keyword + "%");
        query.setParameter(2, page * 3);
        return query.getResultList();
    }

    public List<Board> findAll(Integer page) {
        Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?, 3", Board.class);
        query.setParameter(1, page * 3);
        return query.getResultList();
    }

    public Board findById(int id) {
        Query query = em.createNativeQuery("select * from board_tb where id = ?", Board.class);
        query.setParameter(1, id);
        Board board = (Board) query.getSingleResult();
        return board;
    }

        public BoardResponse.DetailDTO findByIdWithUserAndWithReply(int idx) {
            String q = """
            select bt.id, bt.title, bt.content, bt.user_id, but.username, bt.created_at, 
            rt.id r_id, rt.user_id r_user_id, rut.username, rt.comment from board_tb bt
            left outer join reply_tb rt on bt.id = rt.board_id 
            inner join user_tb but on bt.user_id = but.id 
            left outer join user_tb rut on rt.user_id = rut.id 
            where bt.id = ?
            """;


            Query query = em.createNativeQuery(q);
            query.setParameter(1, idx);

            // 1. 전체 결과 받기
            List<Object[]> rows = (List<Object[]>) query.getResultList();

            // 2. Board 결과가 3개가 중복되기 때문에, 0번지의 값만 가져오기
            Integer id = (Integer) rows.get(0)[0];
            String title = (String) rows.get(0)[1];
            String content = (String) rows.get(0)[2];
            int userId = (Integer) rows.get(0)[3];
            String username = (String) rows.get(0)[4];
            Timestamp createdAt = (Timestamp) rows.get(0)[5];

            BoardResponse.DetailDTO detailDTO = new BoardResponse.DetailDTO(
                    id, title, content, userId, username, createdAt
            );

            // 3 바퀴 돌면서 댓글 추가하기
            for (Object[] row : rows){
                Integer rId = (Integer) row[6];
                Integer rUserId = (Integer) row[7];
                String rUsername = (String) row[8];
                String rComment = (String) row[9];

                BoardResponse.ReplyDTO replyDTO = new BoardResponse.ReplyDTO(
                        rId, rUserId, rUsername, rComment, false
                );

                // 댓글이 없으면 add 안하기
                if(rId != null) detailDTO.addReply(replyDTO);
            }

            return detailDTO;
        }

        @Transactional
        public void save(BoardRequest.SaveDTO requestDTO, int userId) {
            Query query = em.createNativeQuery("insert into board_tb(title, content, user_id, created_at) values(?,?,?, now())");
            query.setParameter(1, requestDTO.getTitle());
            query.setParameter(2, requestDTO.getContent());
            query.setParameter(3, userId);
            query.executeUpdate();
        }
        @Transactional
        public void deleteById(int id) {
            Query query = em.createNativeQuery("delete from board_tb where id = ?");
            query.setParameter(1, id);
            query.executeUpdate();
        }
        @Transactional
        public void update(BoardRequest.UpdateDTO requestDTO, int id) {
            Query query = em.createNativeQuery("update board_tb set title=?, content=? where id = ?");
            query.setParameter(1, requestDTO.getTitle());
            query.setParameter(2, requestDTO.getContent());
            query.setParameter(3, id);
            query.executeUpdate();
        }

}