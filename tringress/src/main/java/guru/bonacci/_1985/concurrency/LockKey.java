package guru.bonacci._1985.concurrency;

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
public class LockKey {

	@PrimaryKeyColumn(name = "identifier", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String identifier;
	
	@PrimaryKeyColumn(name = "when", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
	private long when;
}