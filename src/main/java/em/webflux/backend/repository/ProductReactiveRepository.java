package em.webflux.backend.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import em.webflux.backend.entity.product.Product;

public interface ProductReactiveRepository extends ReactiveMongoRepository<Product, String> {

}
