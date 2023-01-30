package guru.bonacci._1985;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import guru.bonacci._1985.repository.CPoolRepository;

@SpringBootApplication
@EnableTransactionManagement
@EnableCassandraRepositories
public class AdminApp {

	public static void main(String[] args) {
		SpringApplication.run(AdminApp.class, args);
	}
	
	@Bean
  public CommandLineRunner clr(CPoolRepository pools) {
    return args -> {
      pools.deleteAll();
      
//      Vet john = new Vet(UUID.randomUUID(), "John", "Doe", new HashSet<>(Arrays.asList("surgery")));
//      Vet jane = new Vet(UUID.randomUUID(), "Jane", "Doe", new HashSet<>(Arrays.asList("radiology, surgery")));
//      
//      Vet savedJohn = vetRepository.save(john);
//      Vet savedJane = vetRepository.save(jane);
//
//      vetRepository.findAll()
//        .forEach(v -> log.info("Vet: {}", v.getFirstName()));
//      
//      vetRepository.findById(savedJohn.getId())
//        .ifPresent(v -> log.info("Vet by id: {}", v.getFirstName()));
    };
  }
}