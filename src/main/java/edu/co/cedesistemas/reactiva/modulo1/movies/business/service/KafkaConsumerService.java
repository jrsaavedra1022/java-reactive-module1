package edu.co.cedesistemas.reactiva.modulo1.movies.business.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.co.cedesistemas.reactiva.modulo1.movies.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class KafkaConsumerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaConsumerService(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public Object getLastObject(String topic, Class className){
        ConsumerRecord<String, String> lastObject;
        KafkaConfig kafkaConfig = new KafkaConfig();
        kafkaTemplate.setConsumerFactory(kafkaConfig.consumerFactory());

        lastObject = kafkaTemplate.receive(topic, 0, 1);
        String objectReceived = Objects.requireNonNull(lastObject.value());
        return getObjectFromString(objectReceived, className);
    }

    public <T> T getObjectFromString(String json, Class<T> targetType){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, targetType);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
