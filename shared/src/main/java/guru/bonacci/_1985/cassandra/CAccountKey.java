package guru.bonacci._1985.cassandra;

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
public class CAccountKey {

	@PrimaryKeyColumn(name = "pool_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String poolId;

	@PrimaryKeyColumn(name = "account_name", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private String accountName;
}
