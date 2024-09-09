package it.eg.cookbook.error;

import it.eg.cookbook.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {

    private static final String EXCEPTION_MESSAGE_TRAILER = "An exception occurred: {}";

    @Override
    // Scatta quando il payload è formalmente errato (ad. esempio un json non valido)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Message message = ResponseCode.PAYLOAD_ERROR.getMessage(ex.getMessage());

        return new ResponseEntity<>(message, ResponseCode.PAYLOAD_ERROR.getHttpStatus());
    }

    @Override
    // Scatta quando il payload contiene attributi non validi
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Message message = ResponseCode.PAYLOAD_ERROR.getMessage(ex.getMessage());

        return new ResponseEntity<>(message, ResponseCode.PAYLOAD_ERROR.getHttpStatus());
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<Message> handleApiException(final ApiException ex) {
        Message messageError = ex.getCode().getMessage(ex.getMessage());

        log.error(EXCEPTION_MESSAGE_TRAILER, messageError, ex);
        return new ResponseEntity<>(messageError, ex.getCode().getHttpStatus());
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Message> handleGenericException(final Throwable ex) {
        return handleApiException(new ApiException(ex));
    }

}