package nl.avans.ivh5.springmvc.springmvc.loan.websockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by Robin Schellius on 26-9-2016.
 */
@Controller
public class LoanSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public LoanGreeting greeting(LoanMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new LoanGreeting("Hello, " + message.getMessage() + "!");
    }

}
