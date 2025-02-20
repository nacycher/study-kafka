## kafka基础客户端流程
kafka提供了两套API：high Level API、low Level API  
high level API封装了kafka运行细节，使用起来较为简单。

```maven
    <dependency>
        <groupId>org.apache.kafka</groupId>
        <artifactId>kafka_2.12</artifactId>
        <version>3.2.1</version> <!-- 和kafka的版本相同 -->
    </dependency>
```

### 客户端配置
kafka做了很多的配置，来保证高并发、高吞吐、高可用

#### 消费者分组机制
每个消费者是属于一个消费者组的，同一个消息只会被一个组内的消费者消费一次。

#### offset
offset是消息在分区中被消费的位置。
