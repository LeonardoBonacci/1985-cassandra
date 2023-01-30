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
	public CommandLineRunner clr(TransRepository transRepo) {
		return args -> {
//			transRepo.deleteAll();

			var trans1Key = new TransKey("coro.me", System.currentTimeMillis());
			var trans1 = Trans.builder().key(trans1Key).transferId(UUID.randomUUID().toString())
					.poolId("coro").from("me").to("you").amount(BigDecimal.ONE).build();

			var trans1n = trans1.negativeClone();

			System.out.println(trans1);
			System.out.println(trans1n);
			transRepo.saveAll(List.of(trans1, trans1n));

			var trans2Key = new TransKey("coro.you", System.currentTimeMillis());
			var trans2 = Trans.builder().key(trans2Key).transferId(UUID.randomUUID().toString())
					.poolId("coro").from("you").to("me").amount(BigDecimal.TEN).build();

			var trans2n = trans2.negativeClone();

			System.out.println(trans2);
			System.out.println(trans2n);
			transRepo.saveAll(List.of(trans2, trans2n));

			transRepo.findAll().forEach(tr -> log.info("Tr: {}", tr));
			
			transRepo.findAllByKeyPoolAccountId(trans1.getKey().getPoolAccountId()).forEach(tr -> log.info("all: {}", tr));
			
			transRepo.findByKeyPoolAccountIdAndKeyWhen(trans1.getKey().getPoolAccountId(), trans1.getKey().getWhen())
				.ifPresent(tr -> log.info("one: {}", tr));
			
			transRepo.findAllByKeyPoolAccountIdAndKeyWhenGreaterThanEqual(trans1.getKey().getPoolAccountId(), trans1.getKey().getWhen())
				.forEach(tr -> log.info("fromWhen: {}", tr));

			transRepo.findAllByKeyPoolAccountIdAndKeyWhenGreaterThanEqualAndKeyWhenLessThan(trans1.getKey().getPoolAccountId(), 0l, System.currentTimeMillis())
				.forEach(tr -> log.info("fromtoWhen: {}", tr));
			
			System.out.println(transRepo.zoekDeBalans("coro.me"));
		};
	}
}
