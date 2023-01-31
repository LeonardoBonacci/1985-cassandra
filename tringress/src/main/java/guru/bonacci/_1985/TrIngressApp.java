package guru.bonacci._1985;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients
public class TrIngressApp {

	public static void main(String[] args) {
		SpringApplication.run(TrIngressApp.class, args);
	}
}
