package guru.bonacci._1985.tringress.concurrency;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table("john")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lock {

	@PrimaryKey
	private LockKey key;
}