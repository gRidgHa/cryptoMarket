package cryptoMarketDemo.repository;

import cryptoMarketDemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface UserRepository extends JpaRepository<User, String> {
    //User findUserBySecret_key(String secret_key);
    //List<User> findUserByEmail(String email);
}
