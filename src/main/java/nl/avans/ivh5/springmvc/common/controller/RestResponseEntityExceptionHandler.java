package nl.avans.ivh5.springmvc.common.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.Map;

/**
 * Deze controller handelt alle exceptions af voor het project. Je moet wel de specifieke
 * Exception afhandelen met een eigen method, zodat je een juiste foutmelding kunt geven.
 *
 * Zie bv. http://www.baeldung.com/2013/01/31/exception-handling-for-rest-with-spring-3-2/
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Deze methode handelt een standaard Exception af.
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleBookNotFound(Exception ex, WebRequest request) {

        // In de response body kun je een object meegeven. We willen misschien wel meer informatie terug geven
        // dan alleen de error. Je zou dan een eigen ErrorResponse class moeten maken. Deze wordt naar JSON vertaald.
        //
        Map<String, String> responseBody = Collections.singletonMap("error", ex.getMessage());

        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }

    /**
     * Handler methode voor twee Exceptions.
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    @ResponseBody
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

        // Om een goede foutmelding te geven kun je hier een object maken met informatie.
        // Deze is nog niet helemaal goed. Zie ook de handler hierboven.
        String bodyOfResponse[] = new String[]{
            ex.getClass().getName(),
            ex.getMessage(),
            "Je kunt hier zelf meer informatie toevoegen"
        };
        return handleExceptionInternal(
                ex,
                bodyOfResponse,
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }
}