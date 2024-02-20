package shop.mtcoding.blog._core.util;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptTest {

    @Test
    public void login_test(){
        String joinPassword = "1234";
        String enc1Password = BCrypt.hashpw(joinPassword, BCrypt.gensalt());
        System.out.println("JOIN : "+enc1Password);

        String loginPassword = "12345";

        boolean test = BCrypt.checkpw(loginPassword, enc1Password);
        System.out.println(test);
    }

    //$2a$10$ktIEKWhIgqK8Op0Q9RLfc.xmcW3RFOQOFNkeReGoBZtwG7WEIUz7W
    //$2a$10$G56jhvDbCzD81llB.uBwE.AhdpVIJdy4tx43AYcU55UjPydx6jdQi
    @Test
    public void gensalt_test(){
        // hello_3u90gnjfah98
        // hello_gf498ik4mk
        String salt = BCrypt.gensalt();
        System.out.println(salt);
    }

    @Test
    public void hashpw_test(){
        String rawPassword = "1234";
        String encPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        System.out.println(encPassword);
    }
}
