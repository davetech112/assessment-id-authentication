package io.mosip.id_authentication_assessment.controller;

import io.mosip.id_authentication_assessment.dto.HealthDetailsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Controller for health monitoring and service details
 *
 * @author David
 */
@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "health-controller", description = "Health Monitoring Controller")
@Slf4j
public class HealthDetailsController {

    @Value("${mosip.service.name}")
    private String serviceName;

    @Value("${mosip.service.version}")
    private String version;

    @Value("${mosip.service.environment}")
    private String environment;

    @Value("${mosip.id.auth.health.simulate.down:false}")
    private boolean simulateDown;

    @Value("${mosip.id.auth.sample.config}")
    private String sampleConfigValue;

    /**
     * Get detailed health information about the service
     *
     * @return health details response
     */
    @GetMapping(path = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Health Details", description = "Returns detailed health information including service metadata and configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service is UP and healthy",
                    content = @Content(schema = @Schema(implementation = HealthDetailsResponse.class))),
            @ApiResponse(responseCode = "503", description = "Service is DOWN",
                    content = @Content(schema = @Schema(implementation = HealthDetailsResponse.class)))
    })
    public ResponseEntity<HealthDetailsResponse> getHealthDetails() {

        // Check if simulated down status is enabled
        if (simulateDown) {
            log.warn("Health check returning DOWN status (simulated)");

            HealthDetailsResponse response = createHealthResponse("DOWN");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }

        log.info("Health check returning UP status");

        HealthDetailsResponse response = createHealthResponse("UP");
        return ResponseEntity.ok(response);
    }

    /**
     * Create health response with all details
     *
     * @param status the health status (UP/DOWN)
     * @return populated health details response
     */
    private HealthDetailsResponse createHealthResponse(String status) {
        HealthDetailsResponse response = new HealthDetailsResponse();
        response.setStatus(status);
        response.setTimestamp(LocalDateTime.now());
        response.setServiceName(serviceName);
        response.setVersion(version);
        response.setEnvironment(environment);

        // Set configurable property
        HealthDetailsResponse.ConfigurableProperty configProp =
                new HealthDetailsResponse.ConfigurableProperty(
                        "mosip.id.auth.sample.config",
                        sampleConfigValue
                );
        response.setConfigurableProperty(configProp);

        return response;
    }
}
