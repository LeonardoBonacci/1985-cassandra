package guru.bonacci._1985.tringress.concurrency;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table("trrip")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trip {

	@PrimaryKey
	private String poolAccountId;
	
	private String transferId;
}