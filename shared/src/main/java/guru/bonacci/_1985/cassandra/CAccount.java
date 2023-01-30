package guru.bonacci._1985.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table("account")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CAccount {

	@PrimaryKey
	private CAccountKey key;
}