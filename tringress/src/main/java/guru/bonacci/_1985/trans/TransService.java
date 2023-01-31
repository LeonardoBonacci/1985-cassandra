package guru.bonacci._1985.trans;

import org.springframework.stereotype.Service;

import guru.bonacci._1985.concurrency.ConcurrencyCache;
import guru.bonacci._1985.concurrency.TransferConcurrencyException;
import guru.bonacci._1985.concurrency.Tripper;
import guru.bonacci._1985.kafka.KTrans;
import guru.bonacci._1985.validation.TransValidationDelegator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransService {

	private final ConcurrencyCache concurrencyCache;
	private final Tripper tripper;
	private final TransValidationDelegator validator;
	private final TransProducer kProducer;
  

	
  public Mono<KTrans> transfer(KTrans trans) {
  	Mono<Boolean> notBlocked = isBlocked(trans).map(is -> {
       if (is) {
           throw new TransferConcurrencyException();
       }
       return is;
  	});	
  	
  	Mono<Void> readyForTheLaunch = 
	  	tripper.register(trans)
	  		.and(validator.isValid(trans).then());
  	
    return notBlocked.then(readyForTheLaunch)
		    		.then(kProducer.send(trans))
		    		.doOnSuccess(senderResult -> log.info("sent to {}: {}", TransProducer.TOPIC_NAME, senderResult));
  }
  
  private Mono<Boolean> isBlocked(KTrans trans) {
  	var identifier = trans.poolAccountId();
  	return concurrencyCache.isLocked(identifier);
  }
}