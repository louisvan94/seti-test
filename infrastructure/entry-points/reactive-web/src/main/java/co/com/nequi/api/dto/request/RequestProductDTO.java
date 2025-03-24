package co.com.nequi.api.dto.request;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RequestProductDTO {
    private String idFranchise;
    private String idBranchOffice;
    private ProductDTO product;
}
