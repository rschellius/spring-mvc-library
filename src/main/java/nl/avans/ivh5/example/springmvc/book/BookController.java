package nl.avans.ivh5.example.springmvc.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    /**
     * Maak een lijst met boeken waarin we kunnen zoeken.
     */
    public BookController() {}

    /**
     * Retourneer alle boeken in de repository.
     *
     * @return
     */
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String listBooks(Model model) {

        // Aanroep naar de boekenserver - die moet wel draaien...
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<Book> books = restTemplate.getForObject("http://localhost:8090/api/book", ArrayList.class);

        log.debug("findAllBooks", books);

        // Zet de opgevraagde members in het model
        model.addAttribute("books", books);
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveBooks","active");
        // Open de juiste view template als resultaat.
        return "views/book/list";
    }

}
