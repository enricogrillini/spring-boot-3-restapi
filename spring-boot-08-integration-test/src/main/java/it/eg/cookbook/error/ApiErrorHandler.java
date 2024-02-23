package it.eg.cookbook.error;

import it.eg.cookbook.model.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {

    private static final String EXCEPTION_MESSAGE_TRAILER = "An exception occurred: {}";

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ResponseMessage> handleApiException(final ApiException ex) {
        ResponseMessage messageError = ex.getCode().getResponseMessage(ex.getMessage());

        log.error(EXCEPTION_MESSAGE_TRAILER, messageError, ex);
        return new ResponseEntity<>(messageError, ex.getCode().getHttpStatus());
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ResponseMessage> handleGenericException(final Throwable ex) {
        return handleApiException(new ApiException(ex));
    }

}