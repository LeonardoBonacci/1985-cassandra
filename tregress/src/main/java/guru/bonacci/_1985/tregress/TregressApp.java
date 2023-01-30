package guru.bonacci._1985.tregress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import guru.bonacci._1985.tregress.in.KafTrans;
import guru.bonacci._1985.tregress.out.CassTransRepo;
import guru.bonacci._1985.tregress.out.Trip;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class TregressApp {

	private final TransFormer transFormer;
	private final CassTransRepo transRepo;
	private final CassandraOperations cTemplate;

	
	public static void main(String[] args) {
		SpringApplication.run(TregressApp.class, args);
	}

	
	@KafkaListener(topics = "foo", groupId = "i-am-unique")
	public void listenGroupFoo(
				@Payload KafTrans kTrans, 
				@Header(KafkaHeaders.RECEIVED_TIMESTAMP) String timestamp) {
    log.info("Received Message: {} at {}", kTrans, timestamp);
    
    //FIXME remove - this is done in tringress
    cTemplate.insert(new Trip(kTrans.getPoolId() + "." + kTrans.getFrom()));
    
    transRepo.save(
    		transFormer.toLatter(kTrans, Long.valueOf(timestamp)));
	}
}
