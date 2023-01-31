package guru.bonacci._1985.concurrency;

import reactor.core.publisher.Mono;

public interface ConcurrencyCache {

	 Mono<Boolean> isLocked(String identifier);
}	 
