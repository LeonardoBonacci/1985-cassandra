package guru.bonacci._1985.wallet;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci._1985.wallet.shared.PoolType;
import guru.bonacci._1985.wallet.shared.TrValidationRequest;
import guru.bonacci._1985.wallet.shared.TrValidationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class WalletApp {

	private final TransRepository repo;
	
	public static void main(String[] args) {
		SpringApplication.run(WalletApp.class, args);
	}

	@PostMapping("wallet")
	public TrValidationResponse validationInfo(@RequestBody @Valid TrValidationRequest trValRequest) {
		log.info(trValRequest.toString());

		var poolType = Optional.of(PoolType.SARDEX);

		return poolType.map(pType -> {
			var balanceInt = repo.zoekDeBalans(trValRequest.getPoolId() + "." + trValRequest.getFrom());
			var response = new TrValidationResponse(pType, true, true, new BigDecimal(balanceInt).divide(new BigDecimal(100)));
			log.info(response.toString());
			return response;
			
		}).orElse(new TrValidationResponse(null, false, false, BigDecimal.ZERO));
	}
}
