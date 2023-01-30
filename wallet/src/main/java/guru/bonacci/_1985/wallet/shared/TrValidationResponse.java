package guru.bonacci._1985.wallet.shared;

import java.math.BigDecimal;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class TrValidationResponse {

  private PoolType poolType;
  private Boolean fromIsValid;
  private Boolean toIsValid;
  private BigDecimal fromsBalance;
}