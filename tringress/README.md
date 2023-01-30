```
./bin/kafka-topics --bootstrap-server=localhost:9092 --list
./bin/kafka-topics --bootstrap-server=localhost:9092 --delete --topic __transaction_state

./bin/kafka-console-producer --bootstrap-server localhost:9092 --topic transfers
./bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic transfers --from-beginning
```

```
curl -d '{"poolId": "coro", "from":"fooac", "to":"barac", "amount":10.10}' -H "Content-Type: application/json" -X POST localhost:8082/transfers
curl -d '{"poolId": "coro", "from":"barac", "to":"fooac", "amount":100.10}' -H "Content-Type: application/json" -X POST localhost:8082/transfers
```