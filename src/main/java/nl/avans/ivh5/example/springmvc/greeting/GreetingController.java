package nl.avans.ivh5.example.springmvc.greeting;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveGreeting","active");
        return "greeting";
    }

    /**
     * URL om te testen of Exceptions goed worden afgehandeld. Als je in de browser naar /exception gaat
     * zou deze foutmelding zichtbaar moeten zijn.
     *
     * @param model
     * @throws Exception
     */
    @RequestMapping("/exception")
    public void throwException(Model model) throws Exception{
        throw new Exception("Er was een Exception, dit is een test. Als je dit bericht ziet was de Exception zichtbaar.");
    }
}