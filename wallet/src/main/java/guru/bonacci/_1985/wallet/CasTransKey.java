package guru.bonacci._1985.wallet;

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
public class CasTransKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name = "pool_account_id", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
	private String poolAccountId;
	
	@PrimaryKeyColumn(name = "when", ordinal = 1, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
	private long when;
}