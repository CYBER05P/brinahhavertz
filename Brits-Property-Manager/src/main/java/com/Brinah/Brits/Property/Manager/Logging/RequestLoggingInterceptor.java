package com.Brinah.Brits.Property.Manager.Logging;

import com.Brinah.Brits.Property.Manager.Entities.LogEntry;
import com.Brinah.Brits.Property.Manager.Service.Interface.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private final LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();

            boolean isSuperAdmin = auth.getAuthorities().stream()
                    .anyMatch(granted -> granted.getAuthority().equals("ROLE_SUPERADMIN"));

            if (isSuperAdmin) {
                String path = request.getRequestURI();

                String ip = request.getHeader("X-Forwarded-For");
                if (ip == null || ip.isEmpty()) {
                    ip = request.getRemoteAddr();
                }

                log.info("🔒 SUPERADMIN ACCESS: {} accessed [{}] from IP: {}", username, path, ip);

                logService.saveLog(LogEntry.builder()
                        .username(username)
                        .role("ROLE_SUPERADMIN")
                        .ipAddress(ip)
                        .actionType("ACCESS_CONTROLLER")
                        .endpoint(path)
                        .timestamp(LocalDateTime.now())
                        .build());
            }
        }

        return true;
    }
}
