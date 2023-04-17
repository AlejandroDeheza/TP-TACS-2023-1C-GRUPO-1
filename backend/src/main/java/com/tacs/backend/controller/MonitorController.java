package com.tacs.backend.controller;

import com.tacs.backend.dto.EventReportDto;
import com.tacs.backend.dto.ExceptionResponse;
import com.tacs.backend.service.MonitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/monitor")
public class MonitorController {
    private final MonitorService monitorService;

    @GetMapping()
    @Operation(summary = "Get events report", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report ready"),
            @ApiResponse(responseCode = "400", description = "Report querying failed", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    public ResponseEntity<EventReportDto> getReport() {
        int milliseconds = 172800000; // 2 horas
        return ResponseEntity.ok(this.monitorService.getLastEventEntries(milliseconds));
    }

}
