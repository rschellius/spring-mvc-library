package nl.avans.ivh5.springmvc.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Deze ExceptionHandler class is buiten gebruik gezet. De code werkt, maar exceptions
 * worden prima afgevangen door de GlobalExceptionHander, voor zowel de MVC kant als
 * de REST API.
 * Wanneer je specifieke exceptions wilt handlen kan dat nog steeds volgens de
 * structuur in deze class.
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
//    @ExceptionHandler(value = { Exception.class })
//    protected ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
//
//        // In de response body kun je een object meegeven. We willen misschien wel meer informatie terug geven
//        // dan alleen de error. Je zou dan een eigen ErrorResponse class moeten maken. Deze wordt naar JSON vertaald.
//        //
//        Map<String, String> responseBody = Collections.singletonMap("error", ex.getMessage());
//
//        return handleExceptionInternal(
//                ex,
//                responseBody,
//                new HttpHeaders(),
//                HttpStatus.NOT_FOUND,
//                request);
//    }

    /**
     * Deze methode handelt een BookNotFoundException af.
     *
     * @param ex
     * @param request
     * @return
     */
//    @ExceptionHandler(value = { BookNotFoundException.class })
//    protected ResponseEntity<Object> handleBookNotFound(BookNotFoundException ex, WebRequest request) {
//
//        // In de response body kun je een object meegeven. We willen misschien wel meer informatie terug geven
//        // dan alleen de error. Je zou dan een eigen ErrorResponse class moeten maken. Deze wordt naar JSON vertaald.
//        //
//        // Map<String, String> responseBody = Collections.singletonMap("error", ex.getMessage());
//        // Om een goede foutmelding te geven kun je hier een object maken met informatie.
//        // Deze is nog niet helemaal goed. Zie ook de handler hierboven.
//        String responseBody[] = new String[]{
//                ex.getClass().getName(),
//                ex.getMessage(),
//                "Je kunt hier zelf meer informatie toevoegen"
//        };
//
//        return handleExceptionInternal(
//                ex,
//                responseBody,
//                new HttpHeaders(),
//                HttpStatus.NOT_FOUND,
//                request);
//    }

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