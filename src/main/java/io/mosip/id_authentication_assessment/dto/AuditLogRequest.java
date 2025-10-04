package io.mosip.id_authentication_assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for audit log endpoint
 *
 * @author David
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogRequest {

    /** Event type - mandatory */
    @NotBlank(message = "eventType is mandatory")
    private String eventType;

    /** Event description */
    private String description;

    /** User ID - mandatory */
    @NotBlank(message = "userId is mandatory")
    private String userId;
}
