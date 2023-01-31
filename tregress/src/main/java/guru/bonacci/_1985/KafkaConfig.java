package guru.bonacci._1985;

import java.util.List;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;

import guru.bonacci._1985.kafka.KTrans;
import reactor.kafka.receiver.ReceiverOptions;

@Configuration
public class KafkaConfig {
    
	@Bean
  public ReceiverOptions<String, KTrans> kafkaReceiverOptions(KafkaProperties kafkaProperties) {
    ReceiverOptions<String, KTrans> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
    return basicReceiverOptions.subscription(List.of("transfers"));
  }

  @Bean
  public ReactiveKafkaConsumerTemplate<String, KTrans> reactKafkaConsumerTemplate(
  		ReceiverOptions<String, KTrans> kafkaReceiverOptions) {
    return new ReactiveKafkaConsumerTemplate<String, KTrans>(kafkaReceiverOptions);
  }
}