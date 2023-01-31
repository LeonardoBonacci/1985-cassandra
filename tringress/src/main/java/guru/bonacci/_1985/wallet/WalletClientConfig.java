package guru.bonacci._1985.wallet;

import java.time.Clock;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import reactivefeign.ReactiveOptions;
import reactivefeign.client.log.DefaultReactiveLogger;
import reactivefeign.client.log.ReactiveLoggerListener;
import reactivefeign.webclient.WebReactiveOptions;

public class WalletClientConfig {

	@Bean
  public ReactiveOptions reactiveOptions() {
      return new WebReactiveOptions.Builder()
              .setReadTimeoutMillis(1000)
              .setWriteTimeoutMillis(1000)
              .setResponseTimeoutMillis(1000)
              .build();
  }
	
  @Bean
  public ReactiveLoggerListener loggerListener() {
      return new DefaultReactiveLogger(Clock.systemUTC(), LoggerFactory.getLogger(WalletClient.class.getName()));
  }
}
