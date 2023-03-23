package com.necleo.codemonkey.configuration;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.awspring.cloud.core.env.ResourceIdResolver;
import io.awspring.cloud.messaging.config.SimpleMessageListenerContainerFactory;
import io.awspring.cloud.messaging.config.annotation.EnableSqs;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableSqs
public class SQSConfiguration {
  @Bean
  public QueueMessagingTemplate queueMessagingTemplate(
      AmazonSQSAsync amazonSQSAsync, ResourceIdResolver resolver) {
    ObjectMapper mapper =
        new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setSerializedPayloadClass(String.class);
    converter.setObjectMapper(mapper);

    return new QueueMessagingTemplate(amazonSQSAsync, resolver, converter);
  }

  @Bean
  public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(
      AmazonSQSAsync amazonSQSAsync) {
    SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
    factory.setAmazonSqs(amazonSQSAsync);
    factory.setAutoStartup(true);
    factory.setMaxNumberOfMessages(10);
    factory.setTaskExecutor(createDefaultTaskExecutor());
    return factory;
  }

  protected AsyncTaskExecutor createDefaultTaskExecutor() {
    // TODO Move this config to config
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setThreadNamePrefix("SQSExecutor - ");
    threadPoolTaskExecutor.setCorePoolSize(5);
    threadPoolTaskExecutor.setMaxPoolSize(20);
    threadPoolTaskExecutor.setQueueCapacity(10);
    threadPoolTaskExecutor.afterPropertiesSet();
    return threadPoolTaskExecutor;
  }
}
