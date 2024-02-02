package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;
import shop.mtcoding.blog._core.Constant;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em; // jpa가 제공해줌

    public int count() {
        Query query = em.createNativeQuery("select count(*) from board_tb");
        Long count = (Long) query.getSingleResult();
        return count.intValue();
    }

    // 조회니까 트랜잭션 필요없음
    public List<Board> findAll(int page) { // 보드 테이블의 모든 것 가지고 오기
        int value = page*Constant.PAGING_COUNT;
        Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?, ?", Board.class); // 페이지네이션 만들 때는 쿼리 스트링을 쓴다.
        query.setParameter(1, value);
        query.setParameter(2, Constant.PAGING_COUNT);

        List<Board> boardList = query.getResultList(); // 여러건
        return boardList;
    } // 이 결과를 리퀘스트에 담고 뷰 화면 가서 뿌리기

    public BoardResponse.DetailDTO findById(int id) {
        // Entity가 아닌 것은 JPA가 파싱안해준다.
        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.created_at, bt.user_id, ut.username from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = ?");
        query.setParameter(1, id);

        JpaResultMapper rm = new JpaResultMapper();
        BoardResponse.DetailDTO responseDTO = rm.uniqueResult(query, BoardResponse.DetailDTO.class);
        return responseDTO;

    }
}