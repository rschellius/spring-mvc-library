package nl.avans.ivh5.springmvc.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The loan could not be found")
public class LoanNotFoundException extends Exception
{
    private static final long serialVersionUID = 100L;
    private String message = "";

    /**
     * Constructor that enables us to set our own message.
     *
     * @param message The message
     */
    public LoanNotFoundException(String message){
        this.message = message;
    }

    /**
     * Overrides Exception's getMessage() to return our own message
     *
     * @return
     */
    @Override
    public String getMessage(){
        return message;
    }
}