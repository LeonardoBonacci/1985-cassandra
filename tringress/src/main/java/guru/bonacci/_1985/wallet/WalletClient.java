package guru.bonacci._1985.wallet;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import guru.bonacci._1985.rest.TrValidationRequest;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "wallet-client", url="http://localhost:8084", configuration = WalletClientConfig.class)
public interface WalletClient {

	@RequestMapping(value = "/wallet", method = RequestMethod.POST)
	Mono<BigDecimal> getBalance(@RequestBody TrValidationRequest request);
}