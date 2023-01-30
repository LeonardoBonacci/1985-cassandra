package guru.bonacci._1985.trans;

import org.springframework.stereotype.Service;

import guru.bonacci._1985.concurrency.ConcurrencyCache;
import guru.bonacci._1985.concurrency.TransferConcurrencyException;
import guru.bonacci._1985.concurrency.Tripper;
import guru.bonacci._1985.kafka.KTrans;
import guru.bonacci._1985.validation.TransValidationDelegator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransService {

	private final ConcurrencyCache concurrencyCache;
	private final Tripper tripper;
	private final TransValidationDelegator validator;
	private final TransProducer kProducer;
  

  public KTrans transfer(KTrans trans) {
  	if (isBlocked(trans)) {
  		throw new TransferConcurrencyException();
  	}

  	tripper.register(trans);
  	validator.isValid(trans);
  	
    var result = kProducer.send(trans);
    log.info("sent to {}: {}", TransProducer.TOPIC_NAME, result);
    return result;
  }
  
  private boolean isBlocked(KTrans trans) {
  	var identifier = trans.poolAccountId();
  	return concurrencyCache.isLocked(identifier);
  }
}