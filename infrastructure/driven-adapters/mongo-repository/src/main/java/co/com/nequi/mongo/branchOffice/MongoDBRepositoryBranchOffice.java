package co.com.nequi.mongo.branchOffice;

import co.com.nequi.mongo.dto.BranchOfficeDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface MongoDBRepositoryBranchOffice extends ReactiveMongoRepository<BranchOfficeDTO, String>, ReactiveQueryByExampleExecutor<BranchOfficeDTO> {
}
