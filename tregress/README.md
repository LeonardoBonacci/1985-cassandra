```
./bin/kafka-topics --bootstrap-server=localhost:9092 --list
./bin/kafka-topics --bootstrap-server=localhost:9092 --delete --topic __transaction_state
./bin/kafka-topics --bootstrap-server=localhost:9092 --delete --topic transfers

./bin/kafka-console-producer --bootstrap-server localhost:9092 --topic transfers
{"poolId":"coro","transferId":"5966f965-b5ec-41be-b5b5-b7246fb08ab8","from":"barac","to":"fooac","amount":500.00,"when":0}
{"poolId":"coro","transferId":"5966f965-b5ec-41be-b5b5-b7246fb08ab8","from":"barac","to":"fooac","amount":999.99,"when":0}
```

```
curl -d '{"poolId": "coro", "from":"fooac", "to":"barac", "amount":10.10}' -H "Content-Type: application/json" -X POST localhost:8082/transfers
curl -d '{"poolId": "coro", "from":"barac", "to":"fooac", "amount":100.10}' -H "Content-Type: application/json" -X POST localhost:8082/transfers
```