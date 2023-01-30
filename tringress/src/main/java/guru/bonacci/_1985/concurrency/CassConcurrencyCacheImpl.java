package guru.bonacci._1985.concurrency;

import java.time.Duration;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Component
@RequiredArgsConstructor
public class CassConcurrencyCacheImpl implements ConcurrencyCache {

	private final CassandraOperations cTemplate;
	private static final String QUERY = "select * from spring_cassandra.lock where identifier = '%s' and when < %d";
	
	@Override
	public boolean isLocked(String identifier) {
		long now = System.currentTimeMillis();
		
		cTemplate.insert(new Lock(new LockKey(identifier, now)), InsertOptions.builder().ttl(Duration.ofSeconds(1)).build());

		var query = String.format(QUERY, identifier, now);
		log.debug(query);
		var busy = cTemplate.selectOne(query, Lock.class);
		if (busy != null) {
			log.warn("busy {}", busy);
		}
		return busy != null;
	}
}
