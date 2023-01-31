package guru.bonacci._1985.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import guru.bonacci._1985.cassandra.CPool;

public interface PoolRepository extends ReactiveCassandraRepository<CPool, String> {	

}