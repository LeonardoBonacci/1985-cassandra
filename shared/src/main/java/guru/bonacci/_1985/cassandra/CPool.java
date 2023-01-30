package guru.bonacci._1985.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import guru.bonacci._1985.pools.PoolType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table("pool")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CPool {

	@PrimaryKey
	private String poolId;
	
	private PoolType type;
  
}