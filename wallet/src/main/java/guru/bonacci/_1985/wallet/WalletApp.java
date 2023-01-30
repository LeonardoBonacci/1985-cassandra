package guru.bonacci._1985.wallet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class WalletApp {

	public static void main(String[] args) {
		SpringApplication.run(WalletApp.class, args);
	}

	@Bean
	public CommandLineRunner clr(TransRepository transRepo) {
		return args -> {
			transRepo.findAll().forEach(tr -> log.info("Tr: {}", tr));
			
			transRepo.findAllByKeyPoolAccountId("coro.me").forEach(tr -> log.info("all: {}", tr));
			
//			transRepo.findByKeyPoolAccountIdAndKeyWhen(trans1.getKey().getPoolAccountId(), trans1.getKey().getWhen())
//				.ifPresent(tr -> log.info("one: {}", tr));
//			
//			transRepo.findAllByKeyPoolAccountIdAndKeyWhenGreaterThanEqual(trans1.getKey().getPoolAccountId(), trans1.getKey().getWhen())
//				.forEach(tr -> log.info("fromWhen: {}", tr));
//
//			transRepo.findAllByKeyPoolAccountIdAndKeyWhenGreaterThanEqualAndKeyWhenLessThan(trans1.getKey().getPoolAccountId(), 0l, System.currentTimeMillis())
//				.forEach(tr -> log.info("fromtoWhen: {}", tr));
			
			System.out.println(transRepo.zoekDeBalans("coro.me"));
		};
	}
}
