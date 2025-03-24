package co.com.nequi.api;

import co.com.nequi.api.dto.request.BranchOfficeDTO;
import co.com.nequi.api.dto.request.FranchiseDTO;
import co.com.nequi.api.dto.request.ProductDTO;
import co.com.nequi.api.helper.BranchOfficeHelper;
import co.com.nequi.api.helper.FranchiseHelper;
import co.com.nequi.api.helper.ProductHelper;
import co.com.nequi.model.exception.BusinessException;
import co.com.nequi.model.exception.message.ErrorMessage;
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

    @NonNull
    public Mono<ServerResponse> saveFranchise(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(FranchiseDTO.class)
                .map(FranchiseHelper::getFranchise)
                .flatMap(franchiseUseCase::saveFranchise)
                .map(FranchiseHelper::getFranchiseDto)
                .flatMap(franchiseDTO -> ServerResponse.status(201).bodyValue(franchiseDTO));
    }

    @NonNull
    public Mono<ServerResponse> saveBranchOffice(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BranchOfficeDTO.class)
                .map(BranchOfficeHelper::getBranchOffice)
                .flatMap(franchiseUseCase::saveBranchOffice)
                .map(BranchOfficeHelper::getBranchOfficeDto)
                .flatMap(branchOfficeDto -> ServerResponse.status(201).bodyValue(branchOfficeDto));
    }

    @NonNull
    public Mono<ServerResponse> saveProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ProductDTO.class)
                .map(ProductHelper::getProduct)
                .flatMap(franchiseUseCase::saveProduct)
                .map(ProductHelper::getProductDto)
                .flatMap(productDto -> ServerResponse.status(201).bodyValue(productDto));
    }

    @NonNull
    public Mono<ServerResponse> deleteProductById(ServerRequest serverRequest) {
        return Mono.just(serverRequest.pathVariable(ID))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.BAD_REQUEST_ID)))
                .flatMap(franchiseUseCase::deleteProductById)
                .then(ServerResponse.noContent().build());
    }
}
