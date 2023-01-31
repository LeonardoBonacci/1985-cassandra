package guru.bonacci._1985.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import guru.bonacci._1985.cassandra.CAccount;
import guru.bonacci._1985.cassandra.CAccountKey;

public interface AccountRepository extends ReactiveCassandraRepository<CAccount, CAccountKey> {	

}