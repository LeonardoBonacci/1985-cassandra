package guru.bonacci._1985;

import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import guru.bonacci._1985.cassandra.CTrans;
import guru.bonacci._1985.cassandra.CTrip;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CassTransRepoImpl implements CassTransRepo {	

	private final CassandraOperations cTemplate;
	
	
	public void save(CTrans cTrans) {
		log.info("saving {}", cTrans);
		
		var cTransNeg = cTrans.negativeClone();
		log.info("and {}", cTransNeg);

		final CassandraBatchOperations batchOps = cTemplate.batchOps();
    batchOps.delete(new CTrip(cTrans.poolAccountId()));
    batchOps.insert(cTrans);
    batchOps.insert(cTransNeg);
    batchOps.execute();
	}
}