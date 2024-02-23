package it.eg.cookbook.error;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ResponseCode code;

    public ApiException(ResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(ResponseCode code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public ApiException(Throwable cause) {
        this(ResponseCode.SYSTEM_ERROR, cause);
    }

    public ApiException(String cause) {
        this(ResponseCode.BUSINESS_ERROR, cause);
    }

    public ApiException(ResponseCode code) {
        this(code, (String) null);
    }


}
