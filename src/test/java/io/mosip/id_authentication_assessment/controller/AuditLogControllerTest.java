package io.mosip.id_authentication_assessment.controller;

import io.mosip.id_authentication_assessment.dto.AuditLogRequest;
import io.mosip.id_authentication_assessment.dto.AuditLogResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AuditLogController
 *
 * @author David
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuditLogControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testLogAuditEvent_WithValidRequest_ShouldReturn200() {
        // Arrange
        String url = "http://localhost:" + port + "/api/v1/audit/log";
        AuditLogRequest request = new AuditLogRequest("LOGIN", "User login attempt", "user123");

        // Act
        ResponseEntity<AuditLogResponse> response =
                restTemplate.postForEntity(url, request, AuditLogResponse.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testLogAuditEvent_ShouldReturnEventIdAndTimestamp() {
        // Arrange
        String url = "http://localhost:" + port + "/api/v1/audit/log";
        AuditLogRequest request = new AuditLogRequest("LOGOUT", "User logout", "user456");

        // Act
        ResponseEntity<AuditLogResponse> response =
                restTemplate.postForEntity(url, request, AuditLogResponse.class);

        // Assert
        AuditLogResponse body = response.getBody();
        assertNotNull(body);
        assertNotNull(body.getEventId());
        assertNotNull(body.getTimestamp());
        assertNotNull(body.getMessage());
        assertTrue(body.getEventId().matches("[a-f0-9-]{36}")); // UUID format
    }

    @Test
    void testLogAuditEvent_WithMissingEventType_ShouldReturn400() {
        // Arrange
        String url = "http://localhost:" + port + "/api/v1/audit/log";
        AuditLogRequest request = new AuditLogRequest(null, "Test description", "user789");

        // Act
        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testLogAuditEvent_WithMissingUserId_ShouldReturn400() {
        // Arrange
        String url = "http://localhost:" + port + "/api/v1/audit/log";
        AuditLogRequest request = new AuditLogRequest("LOGIN", "Test description", null);

        // Act
        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testLogAuditEvent_WithEmptyEventType_ShouldReturn400() {
        // Arrange
        String url = "http://localhost:" + port + "/api/v1/audit/log";
        AuditLogRequest request = new AuditLogRequest("", "Test description", "user123");

        // Act
        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}