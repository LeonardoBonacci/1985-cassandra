package guru.bonacci._1985.tringress.trans;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trans {

	private String poolId;
  private String transferId; //required
  private String from; //required
  private String to; //required
  private BigDecimal amount; //required
  private long when; // not sent - for response
}