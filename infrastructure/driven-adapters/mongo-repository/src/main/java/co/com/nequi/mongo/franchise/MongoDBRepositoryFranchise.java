package co.com.nequi.mongo.franchise;

import co.com.nequi.mongo.dto.FranchiseDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface MongoDBRepositoryFranchise extends ReactiveMongoRepository<FranchiseDTO, String>, ReactiveQueryByExampleExecutor<FranchiseDTO> {
}
