package guru.bonacci._1985;

import org.springframework.data.cassandra.core.ReactiveCassandraBatchOperations;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.stereotype.Repository;

import guru.bonacci._1985.cassandra.CTrans;
import guru.bonacci._1985.cassandra.CTrip;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CassTransRepo {	

	private final ReactiveCassandraOperations cTemplate;
	
	
	public Mono<Void> save(CTrans cTrans) {
		log.info("saving {}", cTrans);
		
		var cTransNeg = cTrans.negativeClone();
		log.info("and saving {}", cTransNeg);

		final ReactiveCassandraBatchOperations batchOps = cTemplate.batchOps();
    batchOps.delete(new CTrip(cTrans.poolAccountId()));
    batchOps.insert(cTrans);
    batchOps.insert(cTransNeg);
    return batchOps.execute().then();
	}
}