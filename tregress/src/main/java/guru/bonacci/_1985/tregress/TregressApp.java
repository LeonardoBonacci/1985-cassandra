package guru.bonacci._1985.tregress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import guru.bonacci._1985.kafka.KTrans;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class TregressApp {

	private final TransFormer transFormer;
	private final CassTransRepo transRepo;

	
	public static void main(String[] args) {
		SpringApplication.run(TregressApp.class, args);
	}

	
	@KafkaListener(topics = "foo", groupId = "i-am-unique")
	public void listenGroupFoo(
				@Payload KTrans kTrans, 
				@Header(KafkaHeaders.RECEIVED_TIMESTAMP) String timestamp) {
    log.info("Received Message: {} at {}", kTrans, timestamp);
    
    transRepo.save(
    		transFormer.toLatter(kTrans, Long.valueOf(timestamp)));
	}
}
