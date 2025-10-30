package com.Brinah.Brits.Property.Manager.Repositories;

import com.Brinah.Brits.Property.Manager.Entities.LogEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {

    Page<LogEntry> findByUsername(String username, Pageable pageable);

    Page<LogEntry> findByRole(String role, Pageable pageable);

    Page<LogEntry> findByActionType(String actionType, Pageable pageable);

    Page<LogEntry> findByModule(String module, Pageable pageable);

    Page<LogEntry> findBySuccess(boolean success, Pageable pageable);

    Page<LogEntry> findByTimestampBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
