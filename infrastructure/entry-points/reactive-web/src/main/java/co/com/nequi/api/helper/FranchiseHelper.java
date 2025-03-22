package co.com.nequi.api.helper;

import co.com.nequi.api.dto.request.BranchOfficeDTO;
import co.com.nequi.api.dto.request.FranchiseDTO;
import co.com.nequi.api.dto.request.ProductDTO;
import co.com.nequi.model.branchoffice.BranchOffice;
import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.product.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FranchiseHelper {

    public static Franchise getFranchise(FranchiseDTO franchiseDTO) {
        return Franchise.builder().id(franchiseDTO.getId())
                .name(franchiseDTO.getName())
                .offices(franchiseDTO.getOffices().stream().map(FranchiseHelper::getBranchOffice).toList()).build();
    }

    public static BranchOffice getBranchOffice(BranchOfficeDTO branchOfficeDTO) {
        return BranchOffice.builder().id(branchOfficeDTO.getId())
                .name(branchOfficeDTO.getName())
                .products(branchOfficeDTO.getProducts().stream().map(FranchiseHelper::getProduct).toList()).build();
    }

    public static Product getProduct(ProductDTO productDTO) {
        return Product.builder().id(productDTO.getId())
                .name(productDTO.getName())
                .stock(productDTO.getStock()).build();
    }

    public static FranchiseDTO getFranchiseDto(Franchise franchise) {
        if (franchise == null) return null;
        return FranchiseDTO.builder()
                .id(franchise.getId())
                .name(franchise.getName())
                .offices(franchise.getOffices().stream()
                        .map(FranchiseHelper::getBranchOfficeDto)
                        .toList())
                .build();
    }

    public static BranchOfficeDTO getBranchOfficeDto(BranchOffice branchOffice) {
        if (branchOffice == null) return null;
        return BranchOfficeDTO.builder()
                .id(branchOffice.getId())
                .name(branchOffice.getName())
                .products(branchOffice.getProducts().stream()
                        .map(FranchiseHelper::getProductDto)
                        .toList())
                .build();
    }

    public static ProductDTO getProductDto(Product product) {
        if (product == null) return null;
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .build();
    }
}
