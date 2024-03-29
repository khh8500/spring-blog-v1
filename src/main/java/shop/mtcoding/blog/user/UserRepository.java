package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository // 내가 new 하지 않아도 메모리에 띄울 수 있음
public class UserRepository {
    private EntityManager em; // 컴포지션

    public UserRepository(EntityManager em) { // 생성자
        this.em = em;
    }

    @Transactional
    public void save(UserRequest.joinDTO requestDTO) { // 컨트롤러는 정보를 전달하면서 때리고 위임함
        Query query = em.createNativeQuery("insert into user_tb(username, password, email) values (?, ?, ?)");
        query.setParameter(1, requestDTO.getUsername());
        query.setParameter(2, requestDTO.getPassword());
        query.setParameter(3, requestDTO.getEmail());

        query.executeUpdate();
    }

    @Transactional
    public void saveV2(UserRequest.joinDTO requestDTO) {
        User user = new User();// 통신을 통해 받은 데이터를 entity를 만들어서 담아보기
        user.setUsername(requestDTO.getUsername());
        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());

        em.persist(user);
    }

    public User findByUsernameAndPassword(UserRequest.loginDTO requestDTO) {
        Query query = em.createNativeQuery("SELECT * FROM user_tb WHERE username=? AND password=?", User.class); // 알아서 매핑해줌
        query.setParameter(1, requestDTO.getUsername());
        query.setParameter(2, requestDTO.getPassword());

        try {
            User user = (User) query.getSingleResult(); // 결과값이 없어서 터짐
            return user;
        }catch (Exception e) {
            return null;
        }
    }

    public User findByUsername(String username) {
        Query query = em.createNativeQuery("SELECT * FROM user_tb WHERE username=?", User.class); // 알아서 매핑해줌
        query.setParameter(1, username);

        try {
            User user = (User) query.getSingleResult();
            return user;
        }catch (Exception e) {
            return null;
        }
    }
}