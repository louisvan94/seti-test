package co.com.nequi.mongo.branchOffice;

import co.com.nequi.mongo.dto.BranchOfficeDTO;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface MongoDBRepositoryBranchOffice extends ReactiveMongoRepository<BranchOfficeDTO, String>, ReactiveQueryByExampleExecutor<BranchOfficeDTO> {

    @Query("{ '_id': ?0 }")
    @Update("{ '$push': { 'offices': ?1 } }")
    Mono<BranchOfficeDTO> addBranchOffice(String franchiseId, BranchOfficeDTO branchOffice);
}
