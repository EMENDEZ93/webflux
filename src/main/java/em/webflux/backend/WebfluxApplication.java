package em.webflux.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import em.webflux.backend.entity.product.Product;
import em.webflux.backend.repository.ProductReactiveRepository;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class WebfluxApplication implements CommandLineRunner {

	@Autowired
	private ProductReactiveRepository productoReactiveRepository;
	
	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(WebfluxApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		reactiveMongoTemplate.dropCollection("products").subscribe();
		
		Flux.just( new Product("Eureka Server", 33.0),
				   new Product("Elastic Search", 20.0),
				   new Product("Qlik", 15.1)
				)
		.flatMap(product -> productoReactiveRepository.save(product))
		.subscribe(product -> log.info("Insert -> " + product.toString()));
		
	}
}
