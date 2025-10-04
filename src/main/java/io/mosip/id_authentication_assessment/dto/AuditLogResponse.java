package io.mosip.id_authentication_assessment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO for audit log endpoint
 *
 * @author David
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {

    /** Generated event ID */
    private String eventId;

    /** Timestamp of event creation */
    private LocalDateTime timestamp;

    /** Success message */
    private String message;
}
