package co.com.nequi.model.product.gateways;

import co.com.nequi.model.product.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> saveProduct(Product product);
    Mono<Void> deleteProduct(String id);
    Mono<Product> getProductById(String id);
}
