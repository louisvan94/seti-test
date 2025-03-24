package co.com.nequi.usecase.franchise;

import co.com.nequi.model.branchoffice.BranchOffice;
import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseRepository;
import co.com.nequi.model.product.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FranchiseUseCase {

    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise>saveFranchise(Franchise franchise) {
        return franchiseRepository.saveFranchise(franchise);
    }

    public Mono<Franchise>getFranchiseById(String id) {
        return franchiseRepository.getFranchiseById(id);
    }

    public Mono<BranchOffice>saveBranchOffice(BranchOffice branchOffice, String idFranchise) {
        return getFranchiseById(idFranchise)
                .flatMap(franchise ->{
                    franchise.getOffices().add(branchOffice);
                    return saveFranchise(franchise);
                })
                .map(franchise -> branchOffice);
    }

    public Mono<Product> saveProduct(String idBranchOffice, String idFranchise, Product product) {
        return getFranchiseById(idFranchise)
                .flatMap(franchise -> {
                    franchise.getOffices().stream()
                            .filter(branchOffice -> branchOffice.getId().equals(idBranchOffice))
                            .findFirst()
                            .ifPresent(branchOffice -> {
                                if (branchOffice.getProducts() == null) {
                                    branchOffice.setProducts(new ArrayList<>());
                                }
                                branchOffice.getProducts().add(product);
                            });

                    return saveFranchise(franchise);
                })
                .thenReturn(product);
    }

    public Mono<Void> deleteProduct(String idBranchOffice, String idFranchise, String idProduct) {
        return getFranchiseById(idFranchise)
                .flatMap(franchise -> {
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

                    return saveFranchise(franchise);
                })
                .then();
    }

    public Mono<Product> updateStockProduct(String idBranchOffice, String idFranchise, Product product) {
        return getFranchiseById(idFranchise)
                .flatMap(franchise -> {
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

                    return saveFranchise(franchise);
                })
                .thenReturn(product);
    }

    public Mono<List<Product>> maxStockByProduct(String idFranchise) {
        return getFranchiseById(idFranchise) // Obtener la franquicia por ID
                .flatMapMany(franchise -> Flux.fromIterable(franchise.getOffices())) // Convertir las sucursales en Flux
                .flatMap(branchOffice -> {
                    return Flux.fromIterable(branchOffice.getProducts()) // Convertir los productos en Flux
                            .sort(Comparator.comparingInt(Product::getStock).reversed()) // Ordenar por stock descendente
                            .next() // Tomar el producto con más stock
                            .map(product -> {
                                product.setNameBranchOffice(branchOffice.getName()); // Asignar el nombre de la sucursal
                                return product;
                            });
                })
                .collectList(); // Convertir el resultado a una lista de productos
    }

}
