package guru.bonacci._1985.concurrency;

import java.time.Duration;

import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j 
@Component
@RequiredArgsConstructor
public class CassConcurrencyCacheImpl implements ConcurrencyCache {

	private final ReactiveCassandraOperations cTemplate;
	private static final String QUERY = "select * from spring_cassandra.lock where identifier = '%s' and when < %d";
	
	
	@Override
	public Mono<Boolean> isLocked(final String identifier) {
		long now = System.currentTimeMillis();
		
		Mono<Boolean> isLocked = insert(identifier, now)
			.flatMap(_void -> {
			
				var query = String.format(QUERY, identifier, now);
				log.debug(query);
				return cTemplate.selectOne(query, Lock.class);
		}).map(busy -> {
				
				if (busy != null) {
					log.warn("busy {}", busy);
				}
				return busy != null;
		});
		
		return isLocked;
	}
	
	private Mono<Void> insert(String identifier, long timestamp) {
		return cTemplate.insert(
				new Lock(new LockKey(identifier, timestamp)), 
				InsertOptions.builder().ttl(Duration.ofSeconds(1)).build())
			.then();
	}
}
