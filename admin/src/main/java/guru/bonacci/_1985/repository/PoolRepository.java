package guru.bonacci._1985.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.bonacci._1985.domain.Pool;
import guru.bonacci._1985.query.IdAndName;

@Repository
public interface PoolRepository extends JpaRepository<Pool, Long> {

  List<IdAndName> findAllProjectedBy();
}
