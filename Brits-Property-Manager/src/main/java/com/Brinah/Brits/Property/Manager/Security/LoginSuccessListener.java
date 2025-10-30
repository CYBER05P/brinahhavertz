package com.Brinah.Brits.Property.Manager.Security;


import com.Brinah.Brits.Property.Manager.Entities.LogEntry;
import com.Brinah.Brits.Property.Manager.Service.Interface.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest; // Changed from javax.servlet.http
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final HttpServletRequest request;
    private final LogService logService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication auth = event.getAuthentication();
        String username = auth.getName();
        String ipAddress = request.getRemoteAddr();
        // Extract the first role/authority. If none, default to "UNKNOWN".
        String role = auth.getAuthorities().stream().findFirst().map(Object::toString).orElse("UNKNOWN");

        log.info("✅ USER LOGIN: {} from IP: {}", username, ipAddress);

        // Save the login event as a log entry
        logService.saveLog(LogEntry.builder()
                .username(username)
                .role(role)
                .ipAddress(ipAddress)
                .actionType("LOGIN")
                .endpoint("N/A") // Endpoint is not directly available from AuthenticationSuccessEvent, hence N/A
                .timestamp(LocalDateTime.now())
                .build());
    }
}
