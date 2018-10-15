package em.webflux.backend.controller.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import em.webflux.backend.entity.product.Product;
import em.webflux.backend.repository.ProductReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductRestController {

	@Autowired
	private ProductReactiveRepository productoReactiveRepository;

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	@GetMapping("/api/products")
	public Flux<Product> allFluxProduct() {
		Flux<Product> products = productoReactiveRepository.findAll().map(product -> {
			product.setName(product.getName().toUpperCase());
			return product;
		}).doOnNext(product -> log.info(product.getName()));

		return products;
	}

	@GetMapping("/api/{id}/producto")
	public Mono<Product> getProductById(@PathVariable String id) {
		Mono<Product> product = productoReactiveRepository.findById(id);
		return product;
	}

}
