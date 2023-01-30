package guru.bonacci._1985.tringress.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import guru.bonacci._1985.cassandra.CAccount;

public interface AccountRepository extends CassandraRepository<CAccount, String> {	

}