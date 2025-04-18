package co.com.nequi.api.helper;

import co.com.nequi.api.dto.request.BranchOfficeDTO;
import co.com.nequi.api.dto.request.ProductDTO;
import co.com.nequi.model.branchoffice.BranchOffice;
import co.com.nequi.model.product.Product;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.Optional;

@UtilityClass
public class BranchOfficeHelper {

    public static BranchOffice getBranchOffice(BranchOfficeDTO branchOfficeDTO) {
        if (branchOfficeDTO == null) {
            return null;
        }

        return BranchOffice.builder()
                .id(branchOfficeDTO.getId())
                .name(branchOfficeDTO.getName())
                .products(Optional.ofNullable(branchOfficeDTO.getProducts())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(BranchOfficeHelper::getProduct)
                        .toList())
                .build();
    }


    public static Product getProduct(ProductDTO productDTO) {
        return Product.builder().id(productDTO.getId())
                .name(productDTO.getName())
                .stock(productDTO.getStock()).build();
    }

    public static BranchOfficeDTO getBranchOfficeDto(BranchOffice branchOffice) {
        if (branchOffice == null) return null;
        return BranchOfficeDTO.builder()
                .id(branchOffice.getId())
                .name(branchOffice.getName())
                .products(branchOffice.getProducts().stream()
                        .map(BranchOfficeHelper::getProductDto)
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
