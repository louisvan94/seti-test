package co.com.nequi.usecase.franchise;

import co.com.nequi.model.branchoffice.BranchOffice;
import co.com.nequi.model.exception.BusinessException;
import co.com.nequi.model.exception.message.ErrorMessage;
import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseRepository;
import co.com.nequi.model.product.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FranchiseUseCase {

    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise> saveFranchise(Franchise franchise) {
        return franchiseRepository.getFranchiseById(franchise.getId())
                .flatMap(existingFranchise -> Mono.<Franchise>error(new BusinessException(ErrorMessage.FRANCHISE_EXISTS)))
                .switchIfEmpty(franchiseRepository.saveFranchise(franchise));
    }

    public Mono<Franchise>getFranchiseById(String id) {
        return franchiseRepository.getFranchiseById(id)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.FRANCHISE_NOT_EXISTS)));
    }

    public Mono<BranchOffice>saveBranchOffice(BranchOffice branchOffice, String idFranchise) {
        return getFranchiseById(idFranchise)
                .flatMap(franchise ->{
                    boolean exists = franchise.getOffices()
                            .stream()
                            .anyMatch(branchOffice1 -> Objects.equals(branchOffice1.getId(), branchOffice.getId()));
                    if(exists) { return Mono.error(new BusinessException(ErrorMessage.BRANCH_OFFICE_EXISTS)); }
                    franchise.getOffices().add(branchOffice);
                    return franchiseRepository.saveFranchise(franchise);
                })
                .map(franchise -> branchOffice)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.FRANCHISE_NOT_EXISTS)));
    }

    public Mono<Product> saveProduct(String idBranchOffice, String idFranchise, Product product) {
        return getFranchiseById(idFranchise)
                .flatMap(franchise -> {
                    boolean exists = franchise.getOffices()
                            .stream()
                            .anyMatch(branchOffice1 -> Objects.equals(branchOffice1.getId(), idBranchOffice));
                    if(!exists) { return Mono.error(new BusinessException(ErrorMessage.BRANCH_OFFICE_EXISTS)); }
                    franchise.getOffices().stream()
                            .filter(branchOffice -> branchOffice.getId().equals(idBranchOffice))
                            .findFirst()
                            .ifPresent(branchOffice -> {
                                if (branchOffice.getProducts() == null) {
                                    branchOffice.setProducts(new ArrayList<>());
                                }
                                branchOffice.getProducts().add(product);
                            });

                    return franchiseRepository.saveFranchise(franchise);
                })
                .thenReturn(product)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.FRANCHISE_NOT_EXISTS)));
    }

    public Mono<Void> deleteProduct(String idBranchOffice, String idFranchise, String idProduct) {
        return getFranchiseById(idFranchise)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.FRANCHISE_NOT_EXISTS)))
                .flatMap(franchise -> {
                    boolean exists = franchise.getOffices()
                            .stream()
                            .anyMatch(branchOffice1 -> Objects.equals(branchOffice1.getId(), idBranchOffice));
                    if(!exists) { return Mono.error(new BusinessException(ErrorMessage.BRANCH_OFFICE_NOT_EXISTS)); }
                    franchise.getOffices().stream()
                            .filter(branchOffice -> branchOffice.getId().equals(idBranchOffice))
                            .findFirst()
                            .ifPresent(branchOffice -> {
                                if (branchOffice.getProducts() != null) {
                                    branchOffice.setProducts(branchOffice.getProducts().stream()
                                            .filter(product -> !product.getId().equals(idProduct))
                                            .collect(Collectors.toList()));
                                }
                            });

                    return franchiseRepository.saveFranchise(franchise);
                })
                .then();
    }

    public Mono<Product> updateStockProduct(String idBranchOffice, String idFranchise, Product product) {
        return getFranchiseById(idFranchise)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.FRANCHISE_NOT_EXISTS)))
                .flatMap(franchise -> {
                    boolean exists = franchise.getOffices()
                            .stream()
                            .anyMatch(branchOffice1 -> Objects.equals(branchOffice1.getId(), idBranchOffice));
                    if(!exists) { return Mono.error(new BusinessException(ErrorMessage.BRANCH_OFFICE_NOT_EXISTS)); }
                    franchise.getOffices().stream()
                            .filter(branchOffice -> branchOffice.getId().equals(idBranchOffice))
                            .findFirst()
                            .ifPresent(branchOffice -> {
                                if (branchOffice.getProducts() != null) {
                                    branchOffice.setProducts(branchOffice.getProducts().stream()
                                            .map(existingProduct -> existingProduct.getId().equals(product.getId())
                                                    ? existingProduct.toBuilder().stock(product.getStock()).build()
                                                    : existingProduct)
                                            .collect(Collectors.toList()));
                                }
                            });

                    return franchiseRepository.saveFranchise(franchise);
                })
                .thenReturn(product);
    }

    public Mono<Product> updateNameProduct(String idBranchOffice, String idFranchise, Product product) {
        return getFranchiseById(idFranchise)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.FRANCHISE_NOT_EXISTS)))
                .flatMap(franchise -> {
                    boolean exists = franchise.getOffices()
                            .stream()
                            .anyMatch(branchOffice1 -> Objects.equals(branchOffice1.getId(), idBranchOffice));
                    if(!exists) { return Mono.error(new BusinessException(ErrorMessage.BRANCH_OFFICE_NOT_EXISTS)); }
                    franchise.getOffices().stream()
                            .filter(branchOffice -> branchOffice.getId().equals(idBranchOffice))
                            .findFirst()
                            .ifPresent(branchOffice -> {
                                if (branchOffice.getProducts() != null) {
                                    branchOffice.setProducts(branchOffice.getProducts().stream()
                                            .map(existingProduct -> existingProduct.getId().equals(product.getId())
                                                    ? existingProduct.toBuilder().name(product.getName()).build()
                                                    : existingProduct)
                                            .collect(Collectors.toList()));
                                }
                            });

                    return franchiseRepository.saveFranchise(franchise);
                })
                .thenReturn(product);
    }

    public Mono<List<Product>> maxStockByProduct(String idFranchise) {
        return getFranchiseById(idFranchise)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.FRANCHISE_NOT_EXISTS)))
                .flatMapMany(franchise -> Flux.fromIterable(franchise.getOffices()))
                .flatMap(branchOffice -> {
                    return Flux.fromIterable(branchOffice.getProducts())
                            .sort(Comparator.comparingInt(Product::getStock).reversed())
                            .next()
                            .map(product -> {
                                product.setNameBranchOffice(branchOffice.getName());
                                return product;
                            });
                })
                .collectList();
    }

    public Mono<Franchise> updateFranchiseName(Franchise franchise) {
        return franchiseRepository.getFranchiseById(franchise.getId())
                .map(franchise1 -> {
                    franchise1.setName(franchise.getName());
                    return franchise1;
                })
                .flatMap(franchiseRepository::saveFranchise)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.FRANCHISE_NOT_EXISTS)));
    }

    public Mono<BranchOffice> updateBranchOfficeName(BranchOffice branchOffice, String idFranchise) {
        return getFranchiseById(idFranchise)
                .flatMap(franchise -> {
                    List<BranchOffice> offices = franchise.getOffices();

                    return Mono.justOrEmpty(offices.stream()
                                    .filter(office -> Objects.equals(office.getId(), branchOffice.getId()))
                                    .findFirst())
                            .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.BRANCH_OFFICE_NOT_EXISTS)))
                            .flatMap(existingOffice -> {
                                existingOffice.setName(branchOffice.getName());
                                return franchiseRepository.saveFranchise(franchise)
                                        .thenReturn(existingOffice);
                            });
                })
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessage.FRANCHISE_NOT_EXISTS)));
    }

}
