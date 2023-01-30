package guru.bonacci._1985.tregress.in;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafTrans {

	private String poolId; //required
  private String transferId; //required
  private String from; //required
  private String to; //required
  private BigDecimal amount; //required
}