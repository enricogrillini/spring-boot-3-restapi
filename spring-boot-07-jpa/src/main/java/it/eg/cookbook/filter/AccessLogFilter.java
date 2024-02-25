package it.eg.cookbook.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@Component
@Order(1)
public class AccessLogFilter implements Filter {

    public static final String CORRELATION_ID_NAME = "X-REQUESTID";
    public static final String ACTUATOR = "/actuator";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Instant start = Instant.now();

        String correlationId = req.getHeader(CORRELATION_ID_NAME);
        if (ObjectUtils.isEmpty(correlationId)) {
            correlationId = generateUniqueCorrelationId();
        }

        resp.setHeader(CORRELATION_ID_NAME, correlationId);
        try (MDC.MDCCloseable m = MDC.putCloseable(CORRELATION_ID_NAME, correlationId)) {
            // Access Log IN
            if (!req.getRequestURI().startsWith(ACTUATOR)) {
                log.info("IN  - method: {}, URI: {}, protocol {}, host: {}", req.getMethod(), req.getRequestURI(), req.getProtocol(), req.getRemoteHost());
            }
            chain.doFilter(request, response);

            // Access Log OUT
            if (!req.getRequestURI().startsWith("/actuator/health")) {
                log.info("OUT - method: {}, URI: {}, protocol {}, host: {}, status {}, duration {}", req.getMethod(), req.getRequestURI(), req.getProtocol(), req.getRemoteHost(), resp.getStatus(), ChronoUnit.MILLIS.between(start, Instant.now()));
            }
        }
    }

    private String generateUniqueCorrelationId() {
        return UUID.randomUUID().toString();
    }

}