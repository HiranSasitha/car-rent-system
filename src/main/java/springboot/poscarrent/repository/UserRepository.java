package springboot.poscarrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import springboot.poscarrent.model.User;

import java.util.List;

@Repository
@EnableJpaRepositories

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByEmail(String userName);
}
