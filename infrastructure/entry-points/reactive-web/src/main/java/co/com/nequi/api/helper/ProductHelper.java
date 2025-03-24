package co.com.nequi.api.helper;

import co.com.nequi.api.dto.request.ProductDTO;
import co.com.nequi.model.product.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductHelper {

    public static Product getProduct(ProductDTO productDTO) {
        return Product.builder().id(productDTO.getId())
                .idBranchOffice(productDTO.getIdBranchOffice())
                .name(productDTO.getName())
                .stock(productDTO.getStock()).build();
    }

    public static ProductDTO getProductDto(Product product) {
        if (product == null) return null;
        return ProductDTO.builder()
                .id(product.getId())
                .idBranchOffice(product.getIdBranchOffice())
                .name(product.getName())
                .stock(product.getStock())
                .build();
    }
}
