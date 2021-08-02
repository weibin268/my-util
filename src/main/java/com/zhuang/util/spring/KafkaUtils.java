package com.zhuang.util.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.PostConstruct;

@Component
public class KafkaUtils {

    private static KafkaUtils _this;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostConstruct
    private void init() {
        _this = this;
    }

    public static ListenableFuture<SendResult<String, Object>> send(String topic, Object data) {
        return _this.kafkaTemplate.send(topic, data);
    }

}
