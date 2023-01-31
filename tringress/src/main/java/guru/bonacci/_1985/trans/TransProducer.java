package guru.bonacci._1985.trans;

import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci._1985.kafka.KTrans;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TransProducer {

	public final static String TOPIC_NAME = "transfers";
	
  private final ReactiveKafkaProducerTemplate<String, KTrans> kTemplate;

  
	@Transactional
	public Mono<KTrans> send(final KTrans transfer) {
	  return 
	  		sendMessage(TOPIC_NAME, transfer.getPoolId(), transfer)
	  		.map(timestamp -> {
	  			transfer.setWhen(timestamp);
	  			return transfer;
	  		});
	}

  Mono<Long> sendMessage(String topic, String key, KTrans message) {
    return kTemplate.send(topic, key, message)
						.map(result -> result.recordMetadata().timestamp());
  }
}