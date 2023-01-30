package guru.bonacci._1985.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table("trip")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CTrip {

	@PrimaryKey
	private String poolAccountId;
}