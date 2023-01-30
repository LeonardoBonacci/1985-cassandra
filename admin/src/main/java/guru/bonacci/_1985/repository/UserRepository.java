package guru.bonacci._1985.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.bonacci._1985.domain.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
}
