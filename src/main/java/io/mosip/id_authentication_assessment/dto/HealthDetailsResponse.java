package io.mosip.id_authentication_assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthDetailsResponse {

    private String status;
    private LocalDateTime timestamp;
    private String serviceName;
    private String version;
    private String environment;
    private ConfigurableProperty configurableProperty;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConfigurableProperty {
        /** Property name */
        private String name;

        /** Property value */
        private String value;
    }
}
