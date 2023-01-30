package guru.bonacci._1985.tringress.trans;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci._1985.kafka.KTrans;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransProducer {

  private final KafkaTemplate<String, KTrans> kafkaTemplate;

  @Transactional
  public KTrans send(KTrans transfer) {
    long timestamp = sendMessage("foo", transfer.getPoolId(), transfer);
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