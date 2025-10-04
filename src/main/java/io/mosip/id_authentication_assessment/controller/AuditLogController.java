package io.mosip.id_authentication_assessment.controller;

import io.mosip.id_authentication_assessment.dto.AuditLogRequest;
import io.mosip.id_authentication_assessment.dto.AuditLogResponse;
import io.mosip.id_authentication_assessment.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for audit event logging
 *
 * @author David
 */
@RestController
@RequestMapping("/api/v1/audit")
@Tag(name = "audit-controller", description = "Audit Event Logging Controller")
@Slf4j
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    /**
     * Log an audit event
     *
     * @param request the audit log request
     * @return the audit log response with eventId and timestamp
     */
    @PostMapping(path = "/log", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Log Audit Event", description = "Creates and stores an audit event with validation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Audit event logged successfully",
                    content = @Content(schema = @Schema(implementation = AuditLogResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request - validation failed",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<AuditLogResponse> logAuditEvent(@Valid @RequestBody AuditLogRequest request) {

        log.info("Received audit log request - EventType: {}, UserId: {}",
                request.getEventType(), request.getUserId());

        // Save audit event through service
        AuditLogResponse response = auditLogService.saveAuditEvent(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
