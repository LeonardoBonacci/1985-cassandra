package guru.bonacci._1985.trans;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci._1985.kafka.KTrans;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransProducer {

	public final static String TOPIC_NAME = "transfers";
	
  private final KafkaTemplate<String, KTrans> kafkaTemplate;

  @Transactional
  public KTrans send(KTrans transfer) {
    long timestamp = sendMessage(TOPIC_NAME, transfer.getPoolId(), transfer);
    transfer.setWhen(timestamp);
    return transfer;
  }
 
  // exposed for testing
  long sendMessage(String topic, String key, KTrans message) {
    try {
      return kafkaTemplate.send(topic, key, message).get().getRecordMetadata().timestamp();
    } catch (Throwable t) {
      t.printStackTrace();
      return -1;
    }
  }
}