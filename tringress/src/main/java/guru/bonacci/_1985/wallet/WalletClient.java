package guru.bonacci._1985.wallet;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import guru.bonacci._1985.rest.TrValidationRequest;

@FeignClient(name = "wallet-client", url="http://localhost:8084", configuration = WalletClientConfig.class)
public interface WalletClient {

	@RequestMapping(value = "/wallet", method = RequestMethod.POST)
	BigDecimal getBalance(TrValidationRequest request);
}