package guru.bonacci._1985.concurrency;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Component;

import guru.bonacci._1985.cassandra.CTrip;
import guru.bonacci._1985.kafka.KTrans;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Tripper {

	private final CassandraOperations cTemplate;

	
	public void register(KTrans trans) {
		var trip = new CTrip(trans.poolAccountId());
		var tripped = cTemplate.selectOneById(trip.getPoolAccountId(), CTrip.class);
		if (tripped != null) {
			throw new TripException("Still processing your previous transfer");
		}
		cTemplate.insert(trip);
		//ttl to facilitate testing
//		cTemplate.insert(trip, InsertOptions.builder().ttl(Duration.ofSeconds(15)).build());
	}
}
