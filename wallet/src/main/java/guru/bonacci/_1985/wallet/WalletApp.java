package guru.bonacci._1985.wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
	public CommandLineRunner clr(CasTransRepository transRepo) {
		return args -> {
			transRepo.deleteAll();

			var now = System.currentTimeMillis();
			var trans1Key = new CasTransKey("coro.me", now);
			var trans1 = CasTrans.builder().key(trans1Key).transferId(UUID.randomUUID().toString())
					.poolId("coro").from("me").to("you").amount(BigDecimal.ONE).build();
			var trans2 = trans1.negativeClone();

			System.out.println(trans1);
			System.out.println(trans2);
			transRepo.saveAll(List.of(trans1, trans2));
			
			transRepo.findAll().forEach(tr -> log.info("Tr: {}", tr));
			
		};
	}
}
