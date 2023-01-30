# 1985-cassandra

```
docker run -p 9042:9042 --rm --name cassandra -d cassandra:4.0.7
docker exec -it cassandra cqlsh

CREATE KEYSPACE IF NOT EXISTS spring_cassandra WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};
```