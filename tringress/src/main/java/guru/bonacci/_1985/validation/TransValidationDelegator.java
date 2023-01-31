package guru.bonacci._1985.validation;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import guru.bonacci._1985.cassandra.CAccountKey;
import guru.bonacci._1985.cassandra.CPool;
import guru.bonacci._1985.kafka.KTrans;
import guru.bonacci._1985.repository.AccountRepository;
import guru.bonacci._1985.repository.PoolRepository;
import guru.bonacci._1985.rest.TrValidationRequest;
import guru.bonacci._1985.rest.TrValidationResponse;
import guru.bonacci._1985.wallet.WalletClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransValidationDelegator {

  private final ApplicationContext appContext;
  private final WalletClient wallet;
  private final PoolRepository poolRepo;
  private final AccountRepository accountRepo;

  
  public Mono<Boolean> isValid(KTrans trans) {
  	// general validations
		var poolType = poolRepo.findById(trans.getPoolId()).map(CPool::getType)
				.orElseThrow(() -> new InvalidTransferException("pool " + trans.getPoolId() + " is obscure to say the least"));

		if(!accountRepo.existsById(new CAccountKey(trans.getPoolId(), trans.getFrom()))) {
			throw new InvalidTransferException("no account '" + trans.getFrom() + "' in pool '" + trans.getPoolId() + "'");
		}

		if (!accountRepo.existsById(new CAccountKey(trans.getPoolId(), trans.getTo()))) {
			throw new InvalidTransferException("no account '" + trans.getTo() + "' in pool '" + trans.getPoolId() + "'");
		}

		// request balance
		var trValidationRequest = new TrValidationRequest(trans.getPoolId(), trans.getFrom());
    var trValidationResponse = new TrValidationResponse(wallet.getBalance(trValidationRequest));
    log.info("validation response: {}", trValidationResponse);
    
		// pool-specific validations
    var validator = appContext.getBean(poolType.toString().toLowerCase(), PoolTypeBasedValidator.class);
    var validationResult = validator.validate(trValidationResponse, trans.getAmount());

    log.info(validationResult.toString());
    if (!validationResult.isValid()) {
    	throw new InvalidTransferException(validationResult.getErrorMessage());
    }

    return Mono.just(true);
  }
}
