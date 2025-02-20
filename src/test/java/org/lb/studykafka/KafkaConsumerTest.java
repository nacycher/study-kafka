package org.lb.studykafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.lb.studykafka.constant.KafkaConstant;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerTest {
    public static void main(String[] args) {
        String TOPIC = "testTopic";

        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstant.HOST);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");// 取消自动提交 防止消息丢失
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test_group");//指定分组的名称

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(props);
        kafkaConsumer.subscribe(Collections.singletonList(TOPIC));

        double sum_num = 0;
        while (true) {
            ConsumerRecords<String, String> consumerRecord = kafkaConsumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> cr : consumerRecord) {
                double num = Double.parseDouble(cr.value());
                sum_num += num;
                System.out.printf("当前偏移量为：%d，num：%.3f,sum num：%.3f\n", cr.offset(), num, sum_num);
                // 提交消费的偏移量
                kafkaConsumer.commitSync();
            }
        }
    }
}
