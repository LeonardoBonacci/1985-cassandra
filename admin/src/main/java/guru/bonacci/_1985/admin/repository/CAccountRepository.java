package guru.bonacci._1985.admin.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import guru.bonacci._1985.cassandra.CAccount;

public interface CAccountRepository extends CassandraRepository<CAccount, String> {	

}