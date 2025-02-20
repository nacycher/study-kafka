package org.lb.studykafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.lb.studykafka.constant.KafkaConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

@Configuration
public class KafkaProducerTest {

    public static Random rd = new Random(); // 创建一个随机数生成器

    public static void main(String[] args) throws InterruptedException {
        String TOPIC = "testTopic"; // 指定要发送消息的主题名称

        Properties props = new Properties(); // 创建用于配置生产者的属性对象

        // 设置连接到Kafka集群所需的属性
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstant.HOST); // 指定Kafka集群地址
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); // 指定键的序列化器
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); // 指定值的序列化器
        props.put(ProducerConfig.ACKS_CONFIG, "0"); // 设置消息确认级别为0（不等待任何确认）

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(props); // 创建Kafka生产者

        for (int i = 0; i < 999; i++) {
            double d = 0.1 + (3 - 0.1) * rd.nextDouble(); // 生成0.1到3之间的浮点数用来表示间隔时间
            String message = String.format("sleep time:%.2f s", d); // 格式化消息内容
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(TOPIC, message); // 创建要发送的消息记录
            kafkaProducer.send(producerRecord); // 发送消息到主题
            System.out.printf("发送消息“%s”\n", message); // 打印发送的消息内容
            Thread.sleep((long) Math.floor(d * 1000)); // 根据生成的间隔时间进行等待
        }
        // 关闭消息生产者对象
        kafkaProducer.close();
    }

}
