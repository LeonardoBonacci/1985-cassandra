package guru.bonacci._1985.tregress.out;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransRepository extends CrudRepository<Trans, TransKey> {	

	Iterable<Trans> findAllByKeyPoolAccountId(String poolAccountId);
	
	Optional<Trans> findByKeyPoolAccountIdAndKeyWhen(String poolAccountId, long when);
	
	Iterable<Trans> findAllByKeyPoolAccountIdAndKeyWhenGreaterThanEqual(String poolAccountId, long fromWhen);
	
	Iterable<Trans> findAllByKeyPoolAccountIdAndKeyWhenGreaterThanEqualAndKeyWhenLessThan(String poolAccountId, long fromWhen, long toWhen);

	// https://www.youtube.com/watch?v=7u3I3OUES_Y
	@Query("select sum(amount) from spring_cassandra.trans where pool_account_id = :poolAccountId")
	BigDecimal zoekDeBalans(@Param("poolAccountId") String poolAccountId);
}