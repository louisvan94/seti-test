package co.com.nequi.mongo.product;

import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductRepository;
import co.com.nequi.mongo.dto.ProductDTO;
import co.com.nequi.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MongoRepositoryAdapterProduct extends AdapterOperations<Product, ProductDTO, String, MongoDBRepositoryProduct> implements ProductRepository
{

    public MongoRepositoryAdapterProduct(MongoDBRepositoryProduct repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        return super.save(product);
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return super.deleteById(id);
    }

    @Override
    public Mono<Product> getProductById(String id) {
        return super.findById(id);
    }
}