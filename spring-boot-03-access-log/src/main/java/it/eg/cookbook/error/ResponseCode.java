package it.eg.cookbook.error;

import it.eg.cookbook.model.ResponseMessage;
import org.springframework.http.HttpStatus;


public enum ResponseCode {

    OK("Ok", HttpStatus.OK),
    NOT_FOUND("Non trovato", HttpStatus.NOT_FOUND),
    TOKEN_ERRATO("Non trovato", HttpStatus.FORBIDDEN),
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

    public ResponseMessage getResponseMessage(String detail) {
        return ResponseMessage.builder()
                .code(toString())
                .description(getDescription())
                .detail(detail)
                .build();
    }

    ResponseCode(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
