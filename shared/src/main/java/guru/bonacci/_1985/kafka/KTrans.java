package guru.bonacci._1985.kafka;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KTrans {

	private String poolId;
  private String transferId; //required
  private String from; //required
  private String to; //required
  private BigDecimal amount; //required
  
  private long when; // not part of payload - for response
  
  public String poolAccountId() {
    return this.getPoolId() + "." + this.getFrom();
  }
}