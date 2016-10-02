package nl.avans.ivh5.springmvc.springmvc.loan.websockets;

/**
 * Created by Robin Schellius on 26-9-2016.
 */
public class LoanMessage {

    private String message;

    public LoanMessage(){}

    public LoanMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
