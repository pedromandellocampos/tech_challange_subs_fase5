package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

  @Bean
  public NewTopic mailTopic() {
    return TopicBuilder.name("user-notification").build();
  }



}
