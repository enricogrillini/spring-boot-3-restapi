package it.eg.cookbook.error;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ResponseCode code;

    public ApiException(@NotNull ResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(@NotNull ResponseCode code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public ApiException(Throwable cause) {
        this(ResponseCode.SYSTEM_ERROR, cause);
    }

    public ApiException(String cause) {
        this(ResponseCode.BUSINESS_ERROR, cause);
    }

    public ApiException(@NotNull ResponseCode code) {
        this(code, (String) null);
    }


}
