package guru.bonacci._1985.wallet;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;

import guru.bonacci._1985.rest.TrValidationRequest;
import reactor.core.publisher.Mono;

@Component
public class WalletClient {

	@RequestMapping(method = RequestMethod.POST, value = "/wallet")
	public Mono<BigDecimal> getBalance(TrValidationRequest request) {
//		WebClient client = WebClient.create("http://localhost:8084/wallet");
//		return client.post().bodyValue(request).retrieve().bodyToMono(BigDecimal.class);
		return Mono.just(BigDecimal.TEN);
	}
}