package guru.bonacci._1985.tringress.concurrency;

import java.time.Duration;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.stereotype.Component;

import guru.bonacci._1985.concurrency.Trip;
import guru.bonacci._1985.kafka.KTrans;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Tripper {

	private final CassandraOperations cTemplate;

	public void register(KTrans trans) {
		var trip = new Trip(trans.poolAccountId());
		var tripped = cTemplate.selectOneById(trip.getPoolAccountId(), Trip.class);
		if (tripped != null) {
			throw new TripException("Still processing your previous transfer");
		}
		//FIXME ttl to facilitate testing
		cTemplate.insert(trip, InsertOptions.builder().ttl(Duration.ofSeconds(15)).build());
	}
}
