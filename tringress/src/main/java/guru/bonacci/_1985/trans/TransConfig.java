package guru.bonacci._1985.trans;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransConfig {

  @Bean
  public NewTopic internal() {
       return new NewTopic("__transaction_state", 50, (short) 1);
  }

  @Bean
  public NewTopic topic() {
       return new NewTopic("transfers", 1, (short) 1);
  }
}
