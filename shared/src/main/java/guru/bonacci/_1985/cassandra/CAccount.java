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

//	@Query(value = " WITH"
//			+ "	inpool AS (SELECT users.name FROM mysql._1985.user_info AS users"
//			+ "	           JOIN mysql._1985.account AS accounts ON accounts.user_id = users.id and accounts.active = true"
//			+ "	           JOIN mysql._1985.pool AS pools ON pools.id = accounts.pool_id and pools.active = true"
//			+ "	           WHERE users.name = ?1 OR users.name = ?2)"
//			+ "	SELECT name FROM inpool", nativeQuery = true)
//  List<String> findAllNamesInPool(String from, String to);

	@PrimaryKey
	private String userName;

	private String accountName;
	private String poolId;
}