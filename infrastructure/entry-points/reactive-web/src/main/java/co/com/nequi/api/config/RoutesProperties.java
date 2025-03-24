package co.com.nequi.api.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "routes.path-mapping")
public class RoutesProperties {
    private String franchise;
    private String branchOffice;
    private String product;
    private String productId;
    private String productStock;
    private String productName;
    private String productMax;
}
