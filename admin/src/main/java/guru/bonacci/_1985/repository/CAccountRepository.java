package guru.bonacci._1985.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import guru.bonacci._1985.cassandra.CAccount;
import guru.bonacci._1985.cassandra.CAccountKey;

public interface CAccountRepository extends CassandraRepository<CAccount, CAccountKey> {	

}