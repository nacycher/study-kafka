# 该脚本不能直接使用，这里仅记录用到的启动命令
# 具体使用请直接docker compose -f dockercompose.yml up -d
docker create network mycloud

docker run -d --name zookeeper --network mycloud \
-e ALLOW_ANONYMOUS_LOGIN=yes -p 2181:2181 bitnami/zookeeper
docker run -d --name kafka --network mycloud -p 9092:9092 \
 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181/kafka \
 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092 \
 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 bitnami/kafka:3.8




docker exec -it kafka /bin/bash

kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic hello

kafka-console-producer.sh --broker-list localhost:9092 --topic hello

