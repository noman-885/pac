package pk.niit.edu.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.niit.edu.demo.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
