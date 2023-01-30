package guru.bonacci._1985.tringress.trans;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Service;

import guru.bonacci._1985.tringress.concurrent.Trip;
import guru.bonacci._1985.tringress.concurrent.TripException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransService {

	private final CassandraOperations cTemplate;
  private final TransProducer kProducer;
  

  public Trans transfer(Trans trans) {
  	// TODO check and lock/block using redis

  	var trip = new Trip(trans.getPoolId() + "." + trans.getFrom());
  	var tripped = cTemplate.selectOneById(trip.getPoolAccountId(), Trip.class);
  	if (tripped != null) {
  		throw new TripException("Multiple transfers are not allowed.");
  	}
  	cTemplate.insert(trip);

    var result = kProducer.send(trans);
    log.info("sent to {}: {}", "foo", result);
    return result;
  }
}