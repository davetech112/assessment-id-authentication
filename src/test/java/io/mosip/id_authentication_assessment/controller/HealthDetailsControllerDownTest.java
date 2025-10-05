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
 * Unit tests for HealthDetailsController DOWN scenario
 *
 * @author David
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "mosip.id.auth.health.simulate.down=true"
})
class HealthDetailsControllerDownTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testHealthDetailsWhenDownShouldReturn503() {
        // Arrange
        String url = "http://localhost:" + port + "/api/v1/health/details";

        // Act
        ResponseEntity<HealthDetailsResponse> response =
                restTemplate.getForEntity(url, HealthDetailsResponse.class);

        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("DOWN", response.getBody().getStatus());
    }
}
