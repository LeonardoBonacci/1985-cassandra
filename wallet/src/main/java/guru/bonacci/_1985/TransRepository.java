package guru.bonacci._1985;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import guru.bonacci._1985.cassandra.CTrans;
import guru.bonacci._1985.cassandra.CTransKey;
import reactor.core.publisher.Mono;

@Repository
public interface TransRepository extends ReactiveCassandraRepository<CTrans, CTransKey> {	

	// https://www.youtube.com/watch?v=7u3I3OUES_Y
	@Query("select sum(amount) from spring_cassandra.trans where pool_id = :poolId and account_name = :accountName")
	Mono<Long> zoekDeBalans(@Param("poolId") String poolId, @Param("accountName") String accountName);
}