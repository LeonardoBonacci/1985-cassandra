package guru.bonacci._1985.validation;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import guru.bonacci._1985.cassandra.CAccountKey;
import guru.bonacci._1985.cassandra.CPool;
import guru.bonacci._1985.kafka.KTrans;
import guru.bonacci._1985.pools.PoolType;
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
		Mono<PoolType> poolType = poolRepo.findById(trans.getPoolId()).map(CPool::getType)
			.switchIfEmpty(Mono.error(new InvalidTransferException("pool " + trans.getPoolId() + " is obscure to say the least")));	

		Mono<Void> fromExists = accountRepo.findById(new CAccountKey(trans.getPoolId(), trans.getFrom()))
			.switchIfEmpty(Mono.error(new InvalidTransferException("no account '" + trans.getFrom() + "' in pool '" + trans.getPoolId() + "'")))
			.then();	

		Mono<Void> toExists = accountRepo.findById(new CAccountKey(trans.getPoolId(), trans.getTo()))
			.switchIfEmpty(Mono.error(new InvalidTransferException("no account '" + trans.getTo() + "' in pool '" + trans.getPoolId() + "'")))
			.then();	

		Mono<TrValidationResponse> trValidationResponse =
			Mono.zip(poolType, fromExists, toExists)
			.map(tuple -> tuple.getT1())
			.flatMap(pt -> {
				// request balance
				var trValidationRequest = new TrValidationRequest(trans.getPoolId(), trans.getFrom());
				return wallet.getBalance(trValidationRequest)
					.map(TrValidationResponse::new)
					.doOnSuccess(validationResponse -> log.info("validation response: {}", validationResponse));
			});
    
		// pool-specific validations
    var validator = appContext.getBean(poolType.toString().toLowerCase(), PoolTypeBasedValidator.class);

    return trValidationResponse
    	.map(valResp -> validator.validate(valResp, trans.getAmount()))
			.doOnSuccess(valResult -> log.info(valResult.toString()))
			.map(valResult -> {
		    if (!valResult.isValid()) {
		    	throw new InvalidTransferException(valResult.getErrorMessage());
		    }
		    return true;
		});
	}
}
