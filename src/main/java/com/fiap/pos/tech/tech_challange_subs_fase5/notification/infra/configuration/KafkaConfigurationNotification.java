package com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.configuration;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfigurationNotification {

  @Bean
  public ConsumerFactory<String, MailDTO> mailConsumerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, MailDTO.class.getName());

    return new DefaultKafkaConsumerFactory<>(
      config,
      new StringDeserializer(),
      new JsonDeserializer<>(MailDTO.class, false)
    );
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, MailDTO> mailKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, MailDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(mailConsumerFactory());
    return factory;
  }
}
