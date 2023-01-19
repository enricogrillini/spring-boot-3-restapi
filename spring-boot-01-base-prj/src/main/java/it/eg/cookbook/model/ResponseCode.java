package it.eg.cookbook.model;

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
        return new ResponseMessage(toString(), getDescription(), detail);
    }

    ResponseCode(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
