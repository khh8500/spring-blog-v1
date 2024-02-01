package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.mtcoding.blog._core.Constant;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em; // jpa가 제공해줌

    public int count() {
        Query query = em.createNativeQuery("select count(*) from board_tb");
        BigInteger count = (BigInteger) query.getSingleResult();
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
}