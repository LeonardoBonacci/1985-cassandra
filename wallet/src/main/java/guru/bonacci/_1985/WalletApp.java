package guru.bonacci._1985;

import java.math.BigDecimal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci._1985.rest.TrValidationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class WalletApp {

	private final TransRepository transRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(WalletApp.class, args);
	}

	
	@PostMapping("wallet")
	public Mono<BigDecimal> validationInfo(@RequestBody @Valid TrValidationRequest trValRequest) {
		log.info(trValRequest.toString());

		Mono<Long> longBalance = transRepo.zoekDeBalans(trValRequest.getPoolId(), trValRequest.getFrom());
		return longBalance.map(balance -> BigDecimal.valueOf(balance).divide(new BigDecimal(100)))
											.doOnNext(balance -> log.info("{}.{} owns {}", trValRequest.getPoolId(), trValRequest.getFrom(), balance.toString()));
	}
}
