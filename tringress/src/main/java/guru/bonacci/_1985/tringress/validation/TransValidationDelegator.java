package guru.bonacci._1985.tringress.validation;

import java.math.BigDecimal;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import guru.bonacci._1985.tringress.shared.PoolType;
import guru.bonacci._1985.tringress.shared.TrValidationRequest;
import guru.bonacci._1985.tringress.shared.TrValidationResponse;
import guru.bonacci._1985.tringress.trans.Trans;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransValidationDelegator {

  private final ApplicationContext appContext;
//  private final WalletClient wallet;
  

  public boolean isValid(Trans trans) {
		var trValidationRequest = new TrValidationRequest(trans.getPoolId(), trans.getFrom(), trans.getTo());
//    var trValidationResponse = wallet.getValidationInfo(trValidationRequest);
    var trValidationResponse = new TrValidationResponse(PoolType.SARDEX, true, true, BigDecimal.ONE);
    log.debug("validation response: {}", trValidationResponse);
    
    var poolType = trValidationResponse.getPoolType();
    if (poolType == null) {
    	throw new InvalidTransferException("pool " + trans.getPoolId() + " ...");
    }    
    
    var validator = appContext.getBean(poolType.toString().toLowerCase(), PoolTypeBasedValidator.class);

    var validationResult = validator.validate(trValidationResponse, trans.getAmount());
    log.info(validationResult.toString());
    if (!validationResult.isValid()) {
    	throw new InvalidTransferException(validationResult.getErrorMessage());
    }

    return true;
  }
}
