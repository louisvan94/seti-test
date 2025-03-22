package co.com.nequi.model.branchoffice;
import co.com.nequi.model.product.Product;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BranchOffice {
    private String id;
    private String name;
    private List<Product> products;
}
