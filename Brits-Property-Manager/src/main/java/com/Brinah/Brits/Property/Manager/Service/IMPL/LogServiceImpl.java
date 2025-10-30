package com.Brinah.Brits.Property.Manager.Service.IMPL;

import com.Brinah.Brits.Property.Manager.Entities.LogEntry;
import com.Brinah.Brits.Property.Manager.Repositories.LogEntryRepository;
import com.Brinah.Brits.Property.Manager.Service.Interface.LogService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogEntryRepository logEntryRepository;

    @Override
    public void saveLog(LogEntry logEntry) {
        logEntryRepository.save(logEntry);
    }

    @Override
    public Page<LogEntry> getAllLogs(Pageable pageable) {
        return logEntryRepository.findAll(pageable);
    }

    @Override
    public Page<LogEntry> getLogsByUser(String username, Pageable pageable) {
        return logEntryRepository.findByUsername(username, pageable);
    }

    @Override
    public Page<LogEntry> getLogsByRole(String role, Pageable pageable) {
        return logEntryRepository.findByRole(role, pageable);
    }

    // ✅ Fixed: use correct parameter name and align with repository
    @Override
    public Page<LogEntry> getLogsByActionType(String actionType, Pageable pageable) {
        return logEntryRepository.findByActionType(actionType, pageable);
    }

    @Override
    public Page<LogEntry> getLogsByModule(String module, Pageable pageable) {
        return logEntryRepository.findByModule(module, pageable);
    }

    @Override
    public Page<LogEntry> getLogsBySuccess(boolean success, Pageable pageable) {
        return logEntryRepository.findBySuccess(success, pageable);
    }

    @Override
    public Page<LogEntry> getLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return logEntryRepository.findByTimestampBetween(startDate, endDate, pageable);
    }

    @Override
    public byte[] exportLogsToCsv(List<LogEntry> logs) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(out),
                     CSVFormat.DEFAULT.withHeader("ID", "Username", "Role", "IP Address", "Action",
                             "Endpoint", "Module", "Details", "Success", "Timestamp"))) {

            for (LogEntry log : logs) {
                csvPrinter.printRecord(
                        log.getId(),
                        log.getUsername(),
                        log.getRole(),
                        log.getIpAddress(),
                        log.getActionType(),
                        log.getEndpoint(),
                        log.getModule(),
                        log.getDetails(),
                        log.isSuccess(),
                        log.getTimestamp()
                );
            }
            csvPrinter.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error while exporting logs to CSV", e);
        }
    }

    @Override
    public byte[] exportLogsToExcel(List<LogEntry> logs) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Audit Logs");
            Row headerRow = sheet.createRow(0);

            String[] headers = {"ID", "Username", "Role", "IP Address", "Action",
                    "Endpoint", "Module", "Details", "Success", "Timestamp"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (LogEntry log : logs) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(log.getId());
                row.createCell(1).setCellValue(log.getUsername());
                row.createCell(2).setCellValue(log.getRole());
                row.createCell(3).setCellValue(log.getIpAddress() != null ? log.getIpAddress() : "");
                row.createCell(4).setCellValue(log.getActionType());
                row.createCell(5).setCellValue(log.getEndpoint() != null ? log.getEndpoint() : "");
                row.createCell(6).setCellValue(log.getModule() != null ? log.getModule() : "");
                row.createCell(7).setCellValue(log.getDetails() != null ? log.getDetails() : "");
                row.createCell(8).setCellValue(log.isSuccess() ? "SUCCESS" : "FAILURE");
                row.createCell(9).setCellValue(log.getTimestamp().toString());
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error while exporting logs to Excel", e);
        }
    }
}
