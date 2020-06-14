package pelit.hi;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pelit.hi.GreetUtil.buildHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import pelit.hi.logic.GenericMsg;

/**
 * Generic error handling.
 * In real life project, please consider different handlers for different exception types
 * (e.g. invalid user data could return 4xx error codes, while system errors return 5xx codes)
 * 
 * Also have a look at Spring's ResponseEntityExceptionHandler !
 */

@RestControllerAdvice
public class DefaultErrorHandler extends ResponseEntityExceptionHandler  {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<GenericMsg> handleException2(Exception ex, WebRequest req) {
            GenericMsg msg=GenericMsg.builder()
            		.message("Sorry, an error has occured")
            		.details(ex.getMessage())
            		.build();
            return new ResponseEntity<GenericMsg>(
            		msg, 
            		buildHeaders(CONTENT_TYPE, APPLICATION_JSON_VALUE),
            		INTERNAL_SERVER_ERROR);
    }
}