package com.Brinah.Brits.Property.Manager.Service.Interface;

import com.Brinah.Brits.Property.Manager.Entities.LogEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface LogService {

    void saveLog(LogEntry logEntry);

    Page<LogEntry> getAllLogs(Pageable pageable);

    Page<LogEntry> getLogsByUser(String username, Pageable pageable);

    Page<LogEntry> getLogsByRole(String role, Pageable pageable);

    Page<LogEntry> getLogsByActionType(String actionType, Pageable pageable);

    Page<LogEntry> getLogsByModule(String module, Pageable pageable);

    Page<LogEntry> getLogsBySuccess(boolean success, Pageable pageable);

    Page<LogEntry> getLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    byte[] exportLogsToCsv(List<LogEntry> logs);

    byte[] exportLogsToExcel(List<LogEntry> logs);
}
