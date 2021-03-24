package dk.treecreate.api;

import dk.treecreate.api.car.Car;
import dk.treecreate.api.car.CarRepo;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}


	// temp database bypass in form of a H2 in-memory database.
  // This data would get printed to console when you run the app
  @Bean
  ApplicationRunner init(CarRepo repository) {
    return args -> {
      Stream.of("Ferrari", "Jaguar", "Porsche", "Lamborghini", "Bugatti",
        "AMC Gremlin", "Triumph Stag", "Ford Pinto", "Yugo GV").forEach(name -> {
        Car car = new Car();
        car.setName(name);
        repository.save(car);
      });
      repository.findAll().forEach(System.out::println);
    };
  }
}
