package com.Brinah.Brits.Property.Manager.Controllers;

import com.Brinah.Brits.Property.Manager.Entities.LogEntry;
import com.Brinah.Brits.Property.Manager.Service.Interface.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogEntryController {

    private final LogService logService;

    // ✅ Get all logs (ADMIN and SUPERADMIN can view)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    public ResponseEntity<Page<LogEntry>> getAllLogs(Pageable pageable) {
        return ResponseEntity.ok(logService.getAllLogs(pageable));
    }

    // ✅ Filter logs by username
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    public ResponseEntity<Page<LogEntry>> getLogsByUser(@PathVariable String username, Pageable pageable) {
        return ResponseEntity.ok(logService.getLogsByUser(username, pageable));
    }

    // ✅ Filter logs by role
    @GetMapping("/role/{role}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    public ResponseEntity<Page<LogEntry>> getLogsByRole(@PathVariable String role, Pageable pageable) {
        return ResponseEntity.ok(logService.getLogsByRole(role, pageable));
    }

    // ✅ Filter logs by action type
    @GetMapping("/action/{actionType}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    public ResponseEntity<Page<LogEntry>> getLogsByActionType(@PathVariable String actionType, Pageable pageable) {
        return ResponseEntity.ok(logService.getLogsByActionType(actionType, pageable));
    }

    // ✅ Filter logs by module
    @GetMapping("/module/{module}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    public ResponseEntity<Page<LogEntry>> getLogsByModule(@PathVariable String module, Pageable pageable) {
        return ResponseEntity.ok(logService.getLogsByModule(module, pageable));
    }

    // ✅ Filter logs by success (true/false)
    @GetMapping("/success/{success}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    public ResponseEntity<Page<LogEntry>> getLogsBySuccess(@PathVariable boolean success, Pageable pageable) {
        return ResponseEntity.ok(logService.getLogsBySuccess(success, pageable));
    }

    // ✅ Filter logs by date range
    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    public ResponseEntity<Page<LogEntry>> getLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        return ResponseEntity.ok(logService.getLogsByDateRange(startDate, endDate, pageable));
    }

    // ✅ Export logs to CSV (SUPERADMIN only)
    @GetMapping("/export/csv")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<ByteArrayResource> exportLogsToCsv() {
        List<LogEntry> logs = logService.getAllLogs(Pageable.unpaged()).getContent();
        byte[] data = logService.exportLogsToCsv(logs);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=audit-logs.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new ByteArrayResource(data));
    }

    // ✅ Export logs to Excel (SUPERADMIN only)
    @GetMapping("/export/excel")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<ByteArrayResource> exportLogsToExcel() {
        List<LogEntry> logs = logService.getAllLogs(Pageable.unpaged()).getContent();
        byte[] data = logService.exportLogsToExcel(logs);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=audit-logs.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new ByteArrayResource(data));
    }
}
