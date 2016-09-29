package nl.avans.ivh5.example.springmvc.book;

import nl.avans.ivh5.example.springmvc.copy.Copy;
import nl.avans.ivh5.example.springmvc.copy.CopyController;
import nl.avans.ivh5.example.springmvc.loan.Loan;
import nl.avans.ivh5.example.springmvc.member.Member;
import nl.avans.ivh5.example.springmvc.member.MemberController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    // Lees een property uit resources/application.properties
    @Value("${avans.library.bookserver.url}")
    private String urlListAllBooks;

    // De repository waarin we onze boeken zoeken
    private BookRepository bookRepository;
    // De controller waarin we memberinfo opzoeken
    private MemberController memberController;
    // De controller waarin we copyinfo opzoeken
    private CopyController copyController;

    /**
     * Met ModelAttribute kun je attributen (waarden) toevoegen aan het model, zodat
     * je ze in de views kunt gebruiken. Hier kan dat buiten een method .
     * Handig om bv. het 'kruimelpad' (breadcrumbs) of de titel van een pagina in te stellen.
     */
    @ModelAttribute("page")
    public String module() {
        return "BookController";
    }

    // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
    @ModelAttribute("classActiveBooks")
    public String highlightNavMenuItem(){ return "active"; };

    @Autowired
    public BookController(BookRepository bookRepository,
                          MemberController memberController,
                          CopyController copyController) {
        this.bookRepository = bookRepository;
        this.memberController = memberController;
        this.copyController = copyController;
    }

    /**
     * Lees alle boeken die via de REST API beschikbaar zijn.
     * De REST API is een aparte server die je los van de bibliotheek moet starten.
     *
     * @return view die geopend wordt
     * @see <a href="https://bitbucket.org/AEI-informatica/java-spring-boot-restserver">Bitbucket repo</a>
     */
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String listBooksAtRESTServer(Model model) {

        // Aanroep naar de boekenserver - die moet wel draaien...
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<Book> books = null;
        try {
            books = restTemplate.getForObject(urlListAllBooks, ArrayList.class);
        } catch (ResourceAccessException e) {
            logger.error(urlListAllBooks + " is not available - is the server running?");
            // We gooien de exception door. Verderop in deze controller staat
            // een handler hiervoor. Die handelt deze exception af.
            throw e;
        } catch (RestClientException e) {
            logger.error(urlListAllBooks + " is not available - is the server running?");
            // Zie hierboven; wordt afgehandeld.
            throw e;
        }

        logger.debug("listBooksAtRESTServer", books);

        // Zet de opgevraagde members in het model
        model.addAttribute("books", books);
        // Open de juiste view template als resultaat.
        return "views/member/list";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/book/{ean}", method = RequestMethod.GET)
    public String getBookByEAN(@PathVariable Long ean, final ModelMap model) {

        List<Book> books = bookRepository.findById(ean);
        logger.debug("getBookByEAN - " + books.get(0).toString());
        // Bij het lenen van een boek moet je in ons geval een member selecteren.
        List<Member> members = memberController.findAllMembers();
        logger.debug("getBookByEAN - " + members.size() + " members found");
        // Info over de copies van het boek dat we bekijken.
        List<Copy> copies = copyController.findLendingInfoByBookEAN(ean);
        logger.debug("getBookByEAN - " + copies.size() + " copies found");

        // De gebruiker kan bij het bekijken van het boek via de view kiezen
        // of hij het wil lenen. In de view zit een formulier met een knop om een copy te lenen.
        // Daarvoor moet een Loan object ingevuld worden dat we hier meegeven.
        // Dat Loan object wordt via het formulier gePOST naar de LoanController (create).
        Loan loan = new Loan();

        // Zet de gevonden boeken in het model
        // Omdat we hier maar 1 waarde verwachten nemen we listitem 0. Kan tricky zijn.
        model.addAttribute("book", books.get(0));
        model.addAttribute("copies", copies);
        model.addAttribute("members", members);
        // Het Loan object dat in het formulier voor het lenen van een boek wordt ingevuld.
        model.addAttribute("loan", loan);
        // Open de juiste view template als resultaat.
        return "views/book/read";
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
        logger.error("Request " + req.getRequestURL() + " raised " + ex);

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
