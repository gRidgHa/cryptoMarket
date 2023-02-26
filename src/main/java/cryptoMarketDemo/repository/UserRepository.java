package cryptoMarketDemo.repository;

import cryptoMarketDemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {
}
