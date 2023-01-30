package guru.bonacci._1985.tringress.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import guru.bonacci._1985.cassandra.CPool;

public interface PoolRepository extends CassandraRepository<CPool, String> {	

}