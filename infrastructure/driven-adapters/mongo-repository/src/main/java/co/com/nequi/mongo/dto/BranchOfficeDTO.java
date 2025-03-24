package co.com.nequi.mongo.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "branch_offices")
public class BranchOfficeDTO {
    @Id
    private String id;
    private String idFranchise;
    private String name;
    private List<ProductDTO> products;
}
