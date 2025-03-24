package co.com.nequi.mongo.dto;

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
public class BranchOfficeDTO {
    private String id;
    private String idFranchise;
    private String name;
    private List<ProductDTO> products;
}
