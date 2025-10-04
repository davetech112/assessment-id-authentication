package io.mosip.id_authentication_assessment.controller;

import io.mosip.id_authentication_assessment.dto.HealthDetailsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for HealthDetailsController
 *
 * @author David
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "mosip.service.name=test-service",
        "mosip.service.version=1.0.0-test",
        "mosip.service.environment=test",
        "mosip.id.auth.health.simulate.down=false",
        "mosip.id.auth.sample.config=Test Config Value"
})
class HealthDetailsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testHealthDetails_WhenUp_ShouldReturn200() {
        // Arrange
        String url = "http://localhost:" + port + "/api/v1/health/details";

        // Act
        ResponseEntity<HealthDetailsResponse> response =
                restTemplate.getForEntity(url, HealthDetailsResponse.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("UP", response.getBody().getStatus());
    }

    @Test
    void testHealthDetails_ShouldContainAllFields() {
        // Arrange
        String url = "http://localhost:" + port + "/api/v1/health/details";

        // Act
        ResponseEntity<HealthDetailsResponse> response =
                restTemplate.getForEntity(url, HealthDetailsResponse.class);

        // Assert
        HealthDetailsResponse body = response.getBody();
        assertNotNull(body);
        assertNotNull(body.getStatus());
        assertNotNull(body.getTimestamp());
        assertNotNull(body.getServiceName());
        assertNotNull(body.getVersion());
        assertNotNull(body.getEnvironment());
        assertNotNull(body.getConfigurableProperty());
        assertNotNull(body.getConfigurableProperty().getName());
        assertNotNull(body.getConfigurableProperty().getValue());
    }

    @Test
    void testHealthDetails_ShouldReturnCorrectMetadata() {
        // Arrange
        String url = "http://localhost:" + port + "/api/v1/health/details";

        // Act
        ResponseEntity<HealthDetailsResponse> response =
                restTemplate.getForEntity(url, HealthDetailsResponse.class);

        // Assert
        HealthDetailsResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("test-service", body.getServiceName());
        assertEquals("1.0.0-test", body.getVersion());
        assertEquals("test", body.getEnvironment());
        assertEquals("mosip.id.auth.sample.config", body.getConfigurableProperty().getName());
        assertEquals("Test Config Value", body.getConfigurableProperty().getValue());
    }
}