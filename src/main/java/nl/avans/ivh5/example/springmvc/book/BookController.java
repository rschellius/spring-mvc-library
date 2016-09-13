package nl.avans.ivh5.example.springmvc.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    // Lees een property uit resources/application.properties
    @Value("${avans.library.bookserver.url}")
    private String urlListAllBooks;

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
        ArrayList<Book> books = null;
        try {
            books = restTemplate.getForObject(urlListAllBooks, ArrayList.class);
        } catch (ResourceAccessException e) {
            log.error(urlListAllBooks + " is not available - is the server running?");
            // We gooien de exception door. Verderop in deze controller staat
            // een handler hiervoor. Die handelt deze exception af.
            throw e;
        } catch (RestClientException e) {
            log.error(urlListAllBooks + " is not available - is the server running?");
            // Zie hierboven; wordt afgehandeld.
            throw e;
        }

        log.debug("findAllBooks", books);

        // Zet de opgevraagde members in het model
        model.addAttribute("books", books);
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveBooks","active");
        // Open de juiste view template als resultaat.
        return "views/book/list";
    }

    /**
     * Exception handler for this BookController. Deze handler vangt de genoemde
     * exceptions - maar geen andere dus. Maakt specifieke afhandeling van fouten mogelijk.
     *
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler({ResourceAccessException.class, RestClientException.class})
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        log.error("Request " + req.getRequestURL() + " raised " + ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "Ooops :-(");
        mav.addObject("lead", "Daar ging iets mis ...");
        mav.addObject("message", "Het lijkt er op dat we de server op URL <b>" + urlListAllBooks + "</b> niet konden bereiken. Kan het zijn dat die nog niet gestart is?");
        mav.addObject("exception", ex.getMessage());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error/error");
        return mav;
    }
}
