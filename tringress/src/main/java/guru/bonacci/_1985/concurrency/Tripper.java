package guru.bonacci._1985.concurrency;

import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.stereotype.Component;

import guru.bonacci._1985.cassandra.CTrip;
import guru.bonacci._1985.kafka.KTrans;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Tripper {

	private final ReactiveCassandraOperations cTemplate;

	
	public Mono<Void> register(final KTrans trans) {
		var trip = new CTrip(trans.poolAccountId());
		var tripped = cTemplate.selectOneById(trip.getPoolAccountId(), CTrip.class);
		if (tripped != null) {
			throw new TripException("Still processing your previous transfer");
		}
		return cTemplate.insert(trip).then();
	}
}
