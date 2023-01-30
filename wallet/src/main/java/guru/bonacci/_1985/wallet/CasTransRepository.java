package guru.bonacci._1985.wallet;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CasTransRepository extends CrudRepository<CasTrans, CasTransKey> {	

	Iterable<CasTrans> findAllByKeyPoolAccountId(String poolAccountId);
	
	Optional<CasTrans> findByKeyPoolAccountIdAndKeyWhen(String poolAccountId, long when);
	
	Iterable<CasTrans> findAllByKeyPoolAccountIdAndKeyWhenGreaterThanEqual(String poolAccountId, long fromWhen);
	
	Iterable<CasTrans> findAllByKeyPoolAccountIdAndKeyWhenGreaterThanEqualAndKeyWhenLessThan(String poolAccountId, long fromWhen, long toWhen);
}