package guru.bonacci._1985;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

import guru.bonacci._1985.kafka.KTrans;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class KafkaConfig {
 
	@Bean
  public ReactiveKafkaProducerTemplate<String, KTrans> reactKafkaProducerTemplate(KafkaProperties properties) {
      var props = properties.buildProducerProperties();
      return new ReactiveKafkaProducerTemplate<String, KTrans>(SenderOptions.create(props));
  }
}