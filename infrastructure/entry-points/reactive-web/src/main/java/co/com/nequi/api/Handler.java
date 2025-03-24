package co.com.nequi.api;

import co.com.nequi.api.dto.request.*;
import co.com.nequi.api.helper.BranchOfficeHelper;
import co.com.nequi.api.helper.FranchiseHelper;
import co.com.nequi.api.helper.ProductHelper;
import co.com.nequi.model.branchoffice.BranchOffice;
import co.com.nequi.model.exception.BusinessException;
import co.com.nequi.model.exception.message.ErrorMessage;
import co.com.nequi.model.product.Product;
import co.com.nequi.usecase.franchise.FranchiseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Handler {

    private  final FranchiseUseCase franchiseUseCase;
    private static final String ID = "id";
    private static final String ID_FRANCHISE = "id-franchise";
    private static final String ID_BRANCH_OFFICE = "id-branch-office";

    @NonNull
    public Mono<ServerResponse> saveFranchise(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(FranchiseDTO.class)
                .map(FranchiseHelper::getFranchise)
                .flatMap(franchiseUseCase::saveFranchise)
                .map(FranchiseHelper::getFranchiseDto)
                .flatMap(franchiseDTO -> ServerResponse.status(201).bodyValue(franchiseDTO));
    }

    @NonNull
    public Mono<ServerResponse> getFranchiseById(ServerRequest serverRequest) {
        return Mono.just(serverRequest.pathVariable(ID_FRANCHISE))
                .flatMap(franchiseUseCase::getFranchiseById)
                .map(FranchiseHelper::getFranchiseDto)
                .flatMap(response -> ServerResponse.status(200).bodyValue(response));
    }

    @NonNull
    public Mono<ServerResponse> saveBranchOffice(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(RequestBranchOfficeDTO.class)
                .flatMap(requestBranchOfficeDTO -> {
                    BranchOffice branchOffice = BranchOfficeHelper.getBranchOffice(requestBranchOfficeDTO.getBranchOffice());
                    return franchiseUseCase.saveBranchOffice(branchOffice, requestBranchOfficeDTO.getIdFranchise());
                })
                .map(BranchOfficeHelper::getBranchOfficeDto)
                .flatMap(branchOfficeDto -> ServerResponse.status(201).bodyValue(branchOfficeDto));
    }

    @NonNull
    public Mono<ServerResponse> saveProduct(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(RequestProductDTO.class)
                .flatMap(request -> {
                    Product product = ProductHelper.getProduct(request.getProduct());
                    return franchiseUseCase.saveProduct(request.getIdBranchOffice(), request.getIdFranchise(), product);
                })
                .map(ProductHelper::getProductDto)
                .flatMap(response -> ServerResponse.status(201).bodyValue(response));
    }

    @NonNull
    public Mono<ServerResponse> deleteProductById(ServerRequest serverRequest) {
        String idFranchise = serverRequest.pathVariable(ID_FRANCHISE);
        String idBranchOffice = serverRequest.pathVariable(ID_BRANCH_OFFICE);
        String idProduct = serverRequest.pathVariable(ID);

        return Mono.just(idProduct)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.BAD_REQUEST_ID)))
                .flatMap(id -> franchiseUseCase.deleteProduct(idBranchOffice, idFranchise, idProduct))
                .then(ServerResponse.noContent().build());
    }

    @NonNull
    public Mono<ServerResponse> updateStockProduct(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(RequestProductDTO.class)
                .flatMap(request -> {
                    Product product = ProductHelper.getProduct(request.getProduct());
                    return franchiseUseCase.updateStockProduct(request.getIdBranchOffice(), request.getIdFranchise(), product);
                })
                .map(ProductHelper::getProductDto)
                .flatMap(response -> ServerResponse.status(201).bodyValue(response));
    }

    @NonNull
    public Mono<ServerResponse> updateNameProduct(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(RequestProductDTO.class)
                .flatMap(request -> {
                    Product product = ProductHelper.getProduct(request.getProduct());
                    return franchiseUseCase.updateNameProduct(request.getIdBranchOffice(), request.getIdFranchise(), product);
                })
                .map(ProductHelper::getProductDto)
                .flatMap(response -> ServerResponse.status(201).bodyValue(response));
    }

    @NonNull
    public Mono<ServerResponse> getMaxStockProductByOffice(ServerRequest serverRequest) {

        return Mono.just(serverRequest.pathVariable(ID_FRANCHISE))
                .flatMap(franchiseUseCase::maxStockByProduct)
                .flatMap(response -> ServerResponse.status(200).bodyValue(response));
    }

    @NonNull
    public Mono<ServerResponse> updateFranchiseName(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(FranchiseDTO.class)
                .map(FranchiseHelper::getFranchise)
                .flatMap(franchiseUseCase::updateFranchiseName)
                .map(FranchiseHelper::getFranchiseDto)
                .flatMap(franchiseDTO -> ServerResponse.status(201).bodyValue(franchiseDTO));
    }

    @NonNull
    public Mono<ServerResponse> updateBranchOfficeName(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(RequestBranchOfficeDTO.class)
                .flatMap(requestBranchOfficeDTO -> {
                    BranchOffice branchOffice = BranchOfficeHelper.getBranchOffice(requestBranchOfficeDTO.getBranchOffice());
                    return franchiseUseCase.updateBranchOfficeName(branchOffice, requestBranchOfficeDTO.getIdFranchise());
                })
                .map(BranchOfficeHelper::getBranchOfficeDto)
                .flatMap(branchOfficeDto -> ServerResponse.status(201).bodyValue(branchOfficeDto));
    }

}
