package guru.bonacci._1985.validation.pool;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import guru.bonacci._1985.rest.TrValidationResponse;
import guru.bonacci._1985.validation.PoolTypeBasedValidator;
import guru.bonacci._1985.validation.TransValidationResult;

@Component("sardex")
public class SardexPoolTypeValidator implements PoolTypeBasedValidator {

  private static final BigDecimal MIN_BALANCE = BigDecimal.valueOf(-1000);

  
  @Override 
  public TransValidationResult validate(TrValidationResponse info, BigDecimal amount) {
    return hasSufficientFunds(info.getBalance(), amount) ?
          new TransValidationResult(true, null) :
          new TransValidationResult(false, "insufficient balance");
  }    
      
  @Override 
  public boolean hasSufficientFunds(BigDecimal balance, BigDecimal amount) {
  	return balance.subtract(amount).compareTo(MIN_BALANCE) > -1;
  }
}    