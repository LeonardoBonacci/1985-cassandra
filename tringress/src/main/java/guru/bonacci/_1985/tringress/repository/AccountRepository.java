package guru.bonacci._1985.tringress.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import guru.bonacci._1985.cassandra.CAccount;
import guru.bonacci._1985.cassandra.CAccountKey;

public interface AccountRepository extends CassandraRepository<CAccount, CAccountKey> {	

}