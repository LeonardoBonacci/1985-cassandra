package guru.bonacci._1985.tregress.out;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO extrac to shared library
@Data
@Table("moneyasint")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CassTrans implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private CassTransKey key;
	
	private String poolId; //required
  private String transferId; //required
  private String from; //required
  private String to; //required
  private Integer amount; //required
  

  public CassTrans negativeClone() {
    return SerializationUtils.clone(this).negate();
  }
  
  private CassTrans negate() {
  	var oldTo = new String(getTo());
  	var oldFrom = new String(getFrom());
  	this.setFrom(oldTo);
  	this.setTo(oldFrom);
    this.setAmount(this.getAmount() * -1);
    // and, at last, with new values..
    this.getKey().setPoolAccountId(determinePoolAccountId());
    return this;
  }
  
  public String determinePoolAccountId() {
    return this.getPoolId() + "." + this.getFrom();
  }
  
  public static String poolAccountId(CassTrans cTrans) {
    return cTrans.getPoolId() + "." + cTrans.getFrom();
  }
}