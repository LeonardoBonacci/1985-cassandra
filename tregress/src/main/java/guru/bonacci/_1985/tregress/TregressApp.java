package guru.bonacci._1985.tregress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import guru.bonacci._1985.tregress.in.KafTrans;
import guru.bonacci._1985.tregress.out.TransService;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class TregressApp {

	public static void main(String[] args) {
		SpringApplication.run(TregressApp.class, args);
	}


	private final TransFormer transFormer;
	private final TransService transService;
	
	@KafkaListener(topics = "foo", groupId = "i-am-unique")
	public void listenGroupFoo(
				@Payload KafTrans kTrans, 
				@Header(KafkaHeaders.RECEIVED_TIMESTAMP) String timestamp) {
	    System.out.println("Received Message: " + kTrans + " at " + timestamp);
	    var cTrans = transFormer.toLatter(kTrans, Long.valueOf(timestamp));
	    transService.save(cTrans);
	}
}
