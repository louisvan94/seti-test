package co.com.nequi.usecase.franchise;

import co.com.nequi.model.branchoffice.BranchOffice;
import co.com.nequi.model.branchoffice.gateways.BranchOfficeRepository;
import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseRepository;
import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {

    private final ProductRepository productRepository;
    private final FranchiseRepository franchiseRepository;
    private final BranchOfficeRepository branchOfficeRepository;

    public Mono<Franchise>saveFranchise(Franchise franchise) {
        return franchiseRepository.saveFranchise(franchise);
    }

    public Mono<Franchise>getFranchiseById(String id) {
        return franchiseRepository.getFranchiseById(id);
    }

    public Mono<Product>saveProduct(Product product) {
        return productRepository.saveProduct(product);
    }

    public Mono<Void>deleteProductById(String id){
        return productRepository.deleteProduct(id);
    }

    public Mono<Product>updateProductStock(Product product) {
        return productRepository.getProductById(product.getId())
                .map(p -> updateStock(p, product.getStock()))
                .flatMap(productRepository::saveProduct);
    }

    public Mono<Product>updateProductName(Product product) {
        return productRepository.getProductById(product.getId())
                .map(p -> updateName(p, product.getName()))
                .flatMap(productRepository::saveProduct);
    }

    public Mono<Product>getProductById(String id) {
        return productRepository.getProductById(id);
    }

    public Mono<BranchOffice>saveBranchOffice(BranchOffice branchOffice) {
        return branchOfficeRepository.saveBranchOffice(branchOffice);
    }

    public Mono<BranchOffice>getBranchOfficeById(String id) {
        return branchOfficeRepository.getBranchOfficeById(id);
    }

    private Product updateStock(Product product, int stock) {
        return Product.builder().name(product.getName()).id(product.getId()).stock(stock).build();
    }

    private Product updateName(Product product, String name) {
        return Product.builder().name(name).id(product.getId()).stock(product.getStock()).build();
    }
}
