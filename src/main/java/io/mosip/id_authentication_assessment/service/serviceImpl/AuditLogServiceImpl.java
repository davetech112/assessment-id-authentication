package io.mosip.id_authentication_assessment.service.serviceImpl;

import io.mosip.id_authentication_assessment.dto.AuditLogRequest;
import io.mosip.id_authentication_assessment.dto.AuditLogResponse;
import io.mosip.id_authentication_assessment.service.AuditLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of AuditLogService for handling audit event logging
 *
 * @author David
 */
@Service
@Slf4j
public class AuditLogServiceImpl implements AuditLogService {

    /** In-memory storage for audit events */
    private final Map<String, AuditLogRequest> auditEventStore = new ConcurrentHashMap<>();

    @Override
    public AuditLogResponse saveAuditEvent(AuditLogRequest request) {
        // Generate unique event ID
        String eventId = UUID.randomUUID().toString();

        // Get current timestamp
        LocalDateTime timestamp = LocalDateTime.now();

        // Store event in memory
        auditEventStore.put(eventId, request);

        // Log the event
        log.info("Audit event saved - EventID: {}, EventType: {}, UserId: {}",
                eventId, request.getEventType(), request.getUserId());

        // Create and return response
        return new AuditLogResponse(
                eventId,
                timestamp,
                "Audit event logged successfully"
        );
    }
}
