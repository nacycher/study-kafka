## kafka集群部署
- kafka对集群基本是必备的，kafka是高吞吐量，高可用性MQ
- 单机很难将消息全部存下来，集群可以将消息分片，将消息存到不同的broker上，提高吞吐量
- 每个broker上都有备份，在当前broker挂掉后，其他broker可以继续提供服务
- zk负责kafka的注册中心，broker选举。kafka自带有kraft注册中心，但现在主流还是用zk
![](./images/kafka-02-01.png)
