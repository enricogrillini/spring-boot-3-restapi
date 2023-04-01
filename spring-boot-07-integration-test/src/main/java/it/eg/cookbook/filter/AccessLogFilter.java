package it.eg.cookbook.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@Component
@Order(1)
public class AccessLogFilter implements Filter {

    public static final String CORRELATION_ID_NAME = "X-Request-ID";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Instant start = Instant.now();

        String correlationId = req.getHeader(CORRELATION_ID_NAME);
        if (correlationId == null || "".equals(correlationId.trim())) {
            correlationId = generateUniqueCorrelationId();
        }

        resp.setHeader(CORRELATION_ID_NAME, correlationId);
        try (MDC.MDCCloseable m = MDC.putCloseable(CORRELATION_ID_NAME, correlationId)) {
            // Access Log IN
            log.info("IN  - method: {}, URI: {}, protocol {}, host: {}", req.getMethod(), req.getRequestURI(), req.getProtocol(), req.getRemoteHost());

            chain.doFilter(request, response);

            // Access Log OUT
            log.info("OUT - method: {}, URI: {}, protocol {}, host: {}, status {}, duration {}", req.getMethod(), req.getRequestURI(), req.getProtocol(), req.getRemoteHost(), resp.getStatus(), ChronoUnit.MILLIS.between(start, Instant.now()));
        }
    }

    private String generateUniqueCorrelationId() {
        return new StringBuilder(46).append("generated:")
                .append(UUID.randomUUID().toString())
                .toString();
    }

}
