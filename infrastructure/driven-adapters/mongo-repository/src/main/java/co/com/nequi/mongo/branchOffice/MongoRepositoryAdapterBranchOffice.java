package co.com.nequi.mongo.branchOffice;

import co.com.nequi.model.branchoffice.BranchOffice;
import co.com.nequi.model.branchoffice.gateways.BranchOfficeRepository;
import co.com.nequi.mongo.dto.BranchOfficeDTO;
import co.com.nequi.mongo.helper.AdapterOperations;
import co.com.nequi.mongo.helper.BranchOfficeHelper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MongoRepositoryAdapterBranchOffice extends AdapterOperations<BranchOffice, BranchOfficeDTO, String, MongoDBRepositoryBranchOffice> implements BranchOfficeRepository
{

    public MongoRepositoryAdapterBranchOffice(MongoDBRepositoryBranchOffice repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, BranchOffice.class));
    }

    @Override
    public Mono<BranchOffice> saveBranchOffice(String idFranchise, BranchOffice branchOffice) {
        return repository.addBranchOffice(idFranchise, BranchOfficeHelper.getBranchOfficeDto(branchOffice))
                .map(BranchOfficeHelper::getBranchOffice);
    }

    @Override
    public Mono<BranchOffice> getBranchOfficeById(String id) {
        return super.findById(id);
    }
}
