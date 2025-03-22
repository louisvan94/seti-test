package co.com.nequi.api;

import co.com.nequi.api.dto.request.FranchiseDTO;
import co.com.nequi.api.helper.FranchiseHelper;
import co.com.nequi.usecase.franchise.FranchiseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
@RequiredArgsConstructor
public class Handler {
private  final FranchiseUseCase franchiseUseCase;

    @NonNull
    public Mono<ServerResponse> saveFranchise(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(FranchiseDTO.class)
                .map(FranchiseHelper::getFranchise)
                .flatMap(franchiseUseCase::saveFranchise)
                .map(FranchiseHelper::getFranchiseDto)
                .flatMap(franchiseDTO -> ServerResponse.status(201).bodyValue(franchiseDTO));
    }
}
