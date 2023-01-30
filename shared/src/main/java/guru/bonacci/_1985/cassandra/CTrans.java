package guru.bonacci._1985.cassandra;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table("trans")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CTrans implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private CTransKey key;
	
	private String poolId; //required
  private String transferId; //required
  private String from; //required
  private String to; //required
  private Long amount; //required
  

  public CTrans negativeClone() {
    // this is a bit awkward..
  	amount = amount * -1;
    return SerializationUtils.clone(this).negate();
  }
  
  private CTrans negate() {
  	var oldTo = new String(getTo());
  	var oldFrom = new String(getFrom());
  	this.setFrom(oldTo);
  	this.setTo(oldFrom);
  	// .. in combination with this.
    this.setAmount(this.getAmount() * -1);
    // and, at last, with new values..
    this.getKey().setPoolId(getPoolId());
    this.getKey().setAccountName(getFrom());
    return this;
  }
  
  public String poolAccountId() {
    return this.getPoolId() + "." + this.getFrom();
  }
}