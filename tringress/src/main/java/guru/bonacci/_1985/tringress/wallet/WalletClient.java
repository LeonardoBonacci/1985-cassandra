package guru.bonacci._1985.tringress.wallet;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import guru.bonacci._1985.tringress.shared.TrValidationRequest;
import guru.bonacci._1985.tringress.shared.TrValidationResponse;

@FeignClient(name = "wallet-client", url="http://localhost:8084", configuration = WalletClientConfig.class)
public interface WalletClient {

	@RequestMapping(value = "/wallet", method = RequestMethod.POST)
	TrValidationResponse getValidationInfo(TrValidationRequest request);
}