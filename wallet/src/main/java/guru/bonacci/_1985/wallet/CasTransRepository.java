package guru.bonacci._1985.wallet;

import org.springframework.data.repository.CrudRepository;

public interface CasTransRepository extends CrudRepository<CasTrans, CasTransKey> {	

	CasTrans findByPoolAccountId(String from);
}