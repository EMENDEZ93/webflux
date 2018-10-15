package em.webflux.backend.controller.product;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import em.webflux.backend.entity.product.Product;
import em.webflux.backend.repository.ProductReactiveRepository;
import reactor.core.publisher.Flux;

@Controller
public class ProductController {

	@Autowired
	private ProductReactiveRepository productoReactiveRepository;
	
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@GetMapping("/products")
	public String findAllProduct(Model model) {
		Flux<Product> products = productoReactiveRepository.findAll().map(product ->{
			product.setName(product.getName().toUpperCase());
			return product;
		});
		
		products.subscribe( product -> log.info(product.getName()) );
		
		model.addAttribute("products",products);
		
		return "list";
	}
	
	@GetMapping("/data/driver/products")
	public String findAllProductDataDriver(Model model) {
		Flux<Product> products = productoReactiveRepository.findAll().map(product ->{
			product.setName(product.getName().toUpperCase());
			return product;
		}).delayElements(Duration.ofSeconds(3));
		
		products.subscribe( product -> log.info(product.getName()) );
		
		model.addAttribute("products", new ReactiveDataDriverContextVariable(products, 2));
		
		return "list";
	}

	@GetMapping("/chunked/products")
	public String findAllProductChunked(Model model) {
		Flux<Product> products = productoReactiveRepository.findAll().map(product ->{
			product.setName(product.getName().toUpperCase());
			return product;
		}).repeat(5000);
		
		model.addAttribute("products", new ReactiveDataDriverContextVariable(products, 2));
		
		return "list";
	}
	
	@GetMapping("/chunked/mode/view/names/products")
	public String findAllProductModeViewNames(Model model) {
		Flux<Product> products = productoReactiveRepository.findAll().map(product ->{
			product.setName(product.getName().toUpperCase());
			return product;
		}).repeat(5000);
		
		model.addAttribute("products", new ReactiveDataDriverContextVariable(products, 2));
		
		return "list-chunked";
	}
	
}
