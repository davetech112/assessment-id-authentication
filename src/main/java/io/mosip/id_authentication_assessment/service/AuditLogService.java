package io.mosip.id_authentication_assessment.service;


import io.mosip.id_authentication_assessment.dto.AuditLogRequest;
import io.mosip.id_authentication_assessment.dto.AuditLogResponse;

/**
 * Service interface for audit event logging operations
 *
 * @author David
 */
public interface AuditLogService {

    /**
     * Save audit event to storage
     *
     * @param request the audit log request
     * @return the audit log response with eventId and timestamp
     */
    AuditLogResponse saveAuditEvent(AuditLogRequest request);

}
