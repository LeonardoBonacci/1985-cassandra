package guru.bonacci._1985.trans;

import java.util.function.BiConsumer;

import org.springframework.stereotype.Service;

import guru.bonacci._1985.concurrency.ConcurrencyCache;
import guru.bonacci._1985.concurrency.TransferConcurrencyException;
import guru.bonacci._1985.concurrency.Tripper;
import guru.bonacci._1985.kafka.KTrans;
import guru.bonacci._1985.validation.TransValidationDelegator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransService {

	private final ConcurrencyCache concurrencyCache;
	private final Tripper tripper;
	private final TransValidationDelegator validator;
	private final TransProducer kProducer;
  

	
  public Mono<KTrans> transfer(KTrans trans) {
  	BiConsumer<Boolean, SynchronousSink<Boolean>> handler = (blocked, sink) -> {
      if (blocked) {
        sink.error(new TransferConcurrencyException());
      } else {
      	sink.next(blocked);
      }
  	};
  	
  	isBlocked(trans).map(blocked -> {
	  	if (blocked) { 
	  		throw new TransferConcurrencyException();
	  	});
	  ).then();
	  
  	Mono<Void> readyForTheLaunchMono = 
	  	tripper.register(trans)
	  		.and(validator.isValid(trans).then());
  	
    return readyForTheLaunchMono.flatMap(_void -> kProducer.send(trans))
    		.doOnSuccess(senderResult -> log.info("sent to {}: {}", TransProducer.TOPIC_NAME, senderResult));
  }
  
  private Mono<Boolean> isBlocked(KTrans trans) {
  	var identifier = trans.poolAccountId();
  	return concurrencyCache.isLocked(identifier);
  }
  
  
}