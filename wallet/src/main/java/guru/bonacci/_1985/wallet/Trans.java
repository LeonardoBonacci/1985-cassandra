package guru.bonacci._1985.wallet;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table("moneyasint")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trans implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private TransKey key;
	
	private String poolId; //required
  private String transferId; //required
  private String from; //required
  private String to; //required
  private Integer amount; //required
  
  public String determinePoolAccountId() {
    return this.getPoolId() + "." + this.getFrom();
  }
}