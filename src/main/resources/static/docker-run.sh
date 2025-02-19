# 该脚本不能直接使用，这里仅记录用到的启动命令
# 具体使用请直接docker compose -f kafka-docker-compose.yml up -d


docker create network mycloud

# docker run命令启动zk和kafka
docker run -d --name zookeeper --network mycloud \
-e ALLOW_ANONYMOUS_LOGIN=yes -p 2181:2181 bitnami/zookeeper
docker run -d --name kafka --network mycloud -p 9092:9092 \
 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181/kafka \
 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092 \
 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 bitnami/kafka:3.8



# 进入kafka容器，验证kafka是否启动成功
docker exec -it kafka /bin/bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic hello
kafka-console-producer.sh --broker-list localhost:9092 --topic hello

# 验证kafka集群是否启动成功

--create --topic test-topic --bootstrap-server kafka1:9092 \
--replication-factor 3 --partitions 1

# 列出所有topic
docker exec -it kafka1 \
kafka-topics.sh --list --bootstrap-server kafka1:9092

docker exec -it kafka-cluster_kafka1_1 kafka-console-consumer.sh \
--topic test-topic --from-beginning --bootstrap-server kafka1:9092
