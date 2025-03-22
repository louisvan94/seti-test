package co.com.nequi.mongo.franchise;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseRepository;
import co.com.nequi.mongo.dto.FranchiseDTO;
import co.com.nequi.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MongoRepositoryAdapterFranchise extends AdapterOperations<Franchise, FranchiseDTO, String, MongoDBRepositoryFranchise> implements FranchiseRepository
{

    public MongoRepositoryAdapterFranchise(MongoDBRepositoryFranchise repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Franchise.class));
    }

    @Override
    public Mono<Franchise> saveFranchise(Franchise franchise) {
        return super.save(franchise);
    }

    @Override
    public Mono<Franchise> getFranchiseById(String id) {
        return super.findById(id);
    }
}
