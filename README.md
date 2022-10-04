# aws-msk-demo
aws msk demo app

## Kafka setup on local laptop

Intall Kafka

Start Zookeeper

```bash
./bin/zookeeper-server-start.sh config/zookeeper.properties
```

Start Kafka server/broker
```bash
./bin/kafka-server-start.sh config/server.properties
```

## Useful Kafka commands

Describe Kafka topics
```bash
./bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe
```

List Kafka topics
```bash
./bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
```

Delete Kafka topic
```bash
./bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic demo
```

## Pipe Streaming Kafka example 

The pipe stream app basically takes the value on the input topic and puts it on the output topic.
For the pipe stream app to work, the following topics needs to be created:

```bash
./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-pipe-input
```
```bash
./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-pipe-output
```

Start a kafka producer and consumer as follows:

```bash
./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic streams-pipe-input
```
```bash
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic streams-pipe-output --from-beginning
```



