package io.mosip.id_authentication_assessment.service.serviceImpl;

import io.mosip.id_authentication_assessment.dto.AuditLogRequest;
import io.mosip.id_authentication_assessment.dto.AuditLogResponse;
import io.mosip.id_authentication_assessment.service.AuditLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AuditLogServiceImpl
 *
 * @author David
 */
class AuditLogServiceImplTest {

    private AuditLogService auditLogService;

    @BeforeEach
    void setUp() {
        auditLogService = new AuditLogServiceImpl();
    }

    @Test
    void testSaveAuditEventShouldReturnResponse() {
        // Arrange
        AuditLogRequest request = new AuditLogRequest("LOGIN", "User login", "user123");

        // Act
        AuditLogResponse response = auditLogService.saveAuditEvent(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getEventId());
        assertNotNull(response.getTimestamp());
        assertNotNull(response.getMessage());
    }

    @Test
    void testSaveAuditEventShouldGenerateUniqueEventId() {
        // Arrange
        AuditLogRequest request1 = new AuditLogRequest("LOGIN", "User login", "user123");
        AuditLogRequest request2 = new AuditLogRequest("LOGOUT", "User logout", "user456");

        // Act
        AuditLogResponse response1 = auditLogService.saveAuditEvent(request1);
        AuditLogResponse response2 = auditLogService.saveAuditEvent(request2);

        // Assert
        assertNotEquals(response1.getEventId(), response2.getEventId());
    }


}