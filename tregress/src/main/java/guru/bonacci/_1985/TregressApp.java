package guru.bonacci._1985;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;

import guru.bonacci._1985.kafka.KTrans;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class TregressApp implements CommandLineRunner {

	private final ReactiveKafkaConsumerTemplate<String, KTrans> reactKafkaConsumerTemplate;
	private final TransFormer transFormer;
	private final CassTransRepo transRepo;

	
	public static void main(String[] args) {
		SpringApplication.run(TregressApp.class, args);
	}

	
	private Flux<Void> consumeTopic() {
   return reactKafkaConsumerTemplate
           .receiveAutoAck()
           .doOnNext(consumerRecord -> log.info("received value={}", consumerRecord.value()))
           .map(kTrans -> transFormer.toLatter(kTrans.value(), kTrans.timestamp()))
           .flatMap(transRepo::save)
           .doOnError(throwable -> log.error("oops : {}", throwable.getMessage()));
	}
	 
  @Override
  public void run(String... args) {
      // trigger consumption
      consumeTopic().subscribe();
  }
}
