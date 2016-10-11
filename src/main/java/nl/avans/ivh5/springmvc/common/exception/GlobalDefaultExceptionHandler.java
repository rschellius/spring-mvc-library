package nl.avans.ivh5.springmvc.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalDefaultExceptionHandler {

    // Installeer de logger.
    private final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);;
    // De default view voor foutmeldingen
    public static final String DEFAULT_ERROR_VIEW = "error/error";

    /**
     * Als er een exception optreedt die nergens anders afgehandeld wordt, wordt hij hier opgevangen.
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException springmvc
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            logger.error("Error in defaultErrorHandler, throwing exception");
            throw e;
        }

        // Otherwise setup and send the user to a default error-view.
        logger.error("defaultErrorHandler - " + e.getMessage());
        e.printStackTrace();
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("title", "Error: " + e.getClass().getName());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }


}