package co.com.nequi.model.branchoffice.gateways;

import co.com.nequi.model.branchoffice.BranchOffice;
import reactor.core.publisher.Mono;

public interface BranchOfficeRepository {
    Mono<BranchOffice> saveBranchOffice(String idFranchise, BranchOffice branchOffice);
    Mono<BranchOffice> getBranchOfficeById(String id);
}
