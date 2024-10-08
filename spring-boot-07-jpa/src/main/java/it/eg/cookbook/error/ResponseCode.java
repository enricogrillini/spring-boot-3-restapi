package it.eg.cookbook.error;

import it.eg.cookbook.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;


public enum ResponseCode {

    OK("Ok", HttpStatus.OK),
    NOT_FOUND("Non trovato", HttpStatus.NOT_FOUND),
    TOKEN_ERRATO("Non trovato", HttpStatus.FORBIDDEN),

    PAYLOAD_ERROR("Errore in validazione payload", HttpStatus.BAD_REQUEST),
    BUSINESS_ERROR("Errore generico", HttpStatus.BAD_REQUEST),
    SYSTEM_ERROR("Errore di sistema", HttpStatus.INTERNAL_SERVER_ERROR);

    private String description;
    private HttpStatus httpStatus;

    public String getDescription() {
        return description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Message getMessage(String detail) {
        return new Message()
                .code(toString())
                .description(getDescription())
                .detail(detail);
    }

    ResponseCode(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
