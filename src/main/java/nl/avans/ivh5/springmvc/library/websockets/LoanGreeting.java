package nl.avans.ivh5.springmvc.springmvc.loan.websockets;

/**
 * Created by Robin Schellius on 26-9-2016.
 */
public class LoanGreeting {

    private String content;

    public LoanGreeting() {
    }

    public LoanGreeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
