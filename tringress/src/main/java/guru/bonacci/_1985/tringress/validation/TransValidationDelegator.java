package guru.bonacci._1985.tringress.validation;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import guru.bonacci._1985.cassandra.CPool;
import guru.bonacci._1985.kafka.KTrans;
import guru.bonacci._1985.rest.TrValidationRequest;
import guru.bonacci._1985.tringress.repository.AccountRepository;
import guru.bonacci._1985.tringress.repository.PoolRepository;
import guru.bonacci._1985.tringress.wallet.WalletClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransValidationDelegator {

  private final ApplicationContext appContext;
  private final WalletClient wallet;
  private final PoolRepository poolRepo;
  private final AccountRepository accountRepo;

  public boolean isValid(KTrans trans) {
  	// general validations
		var poolType = poolRepo.findById(trans.getPoolId()).map(CPool::getType)
				.orElseThrow(() -> new InvalidTransferException("pool " + trans.getPoolId() + " ..."));

		accountRepo.findById(trans.getFrom()) //TODO different query
				.orElseThrow(() -> new InvalidTransferException("pool " + trans.getPoolId() + " ..."));

		accountRepo.findById(trans.getTo()) //TODO different query
		.orElseThrow(() -> new InvalidTransferException("pool " + trans.getPoolId() + " ..."));

		// request balance
		var trValidationRequest = new TrValidationRequest(trans.getPoolId(), trans.getFrom());
    var trValidationResponse = wallet.getBalance(trValidationRequest);
    log.debug("validation response: {}", trValidationResponse);
    
		// pool-specific validations
    var validator = appContext.getBean(poolType.toString().toLowerCase(), PoolTypeBasedValidator.class);
    var validationResult = validator.validate(trValidationResponse, trans.getAmount());

    log.info(validationResult.toString());
    if (!validationResult.isValid()) {
    	throw new InvalidTransferException(validationResult.getErrorMessage());
    }

    return true;
  }
}
