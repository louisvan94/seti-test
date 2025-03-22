package co.com.nequi.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes.path-mapping")
public class RoutesProperties {
    private String franchise;
    private String branchOffice;
    private String product;
}
