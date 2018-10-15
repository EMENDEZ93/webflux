package em.webflux.backend.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import em.webflux.backend.entity.product.Product;
import em.webflux.backend.repository.ProductReactiveRepository;
import reactor.core.publisher.Flux;

@Controller
public class ProductController {

	@Autowired
	private ProductReactiveRepository productoReactiveRepository;
	
	
	@GetMapping("/products")
	public String findAllProduct(Model model) {
		Flux<Product> products = productoReactiveRepository.findAll();
		model.addAttribute("products",products);
		
		return "list";
	}
	
}
