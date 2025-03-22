package co.com.nequi.mongo.product;

import co.com.nequi.mongo.dto.ProductDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface MongoDBRepositoryProduct extends ReactiveMongoRepository<ProductDTO, String>, ReactiveQueryByExampleExecutor<ProductDTO> {
}
