package guru.bonacci._1985.cassandra;

import java.io.Serializable;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@PrimaryKeyClass
@NoArgsConstructor
@AllArgsConstructor
public class CTransKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name = "pool_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String poolId;

	@PrimaryKeyColumn(name = "account_name", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private String accountName;
	
	@PrimaryKeyColumn(name = "when", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
	private long when;
}
