package guru.bonacci._1985.admin.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import guru.bonacci._1985.cassandra.CPool;

public interface CPoolRepository extends CassandraRepository<CPool, String> {	

}