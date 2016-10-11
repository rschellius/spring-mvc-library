package nl.avans.ivh5.springmvc.library.controller;

import nl.avans.ivh5.springmvc.common.exception.BookNotFoundException;
import nl.avans.ivh5.springmvc.library.model.Book;
import nl.avans.ivh5.springmvc.library.model.Copy;
import nl.avans.ivh5.springmvc.library.model.Loan;
import nl.avans.ivh5.springmvc.library.model.Member;
import nl.avans.ivh5.springmvc.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    // De repository waarin we onze boeken zoeken
    private BookService bookService = null;

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
    public String highlightNavMenuItem(){ return "active"; }

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
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

        logger.info("listBooksAtRESTServer");
        ArrayList<Book> books = bookService.listBooksAtRESTServer();
        // Zet de opgevraagde boeken in het model
        model.addAttribute("books", books);
        // Open de juiste view template als resultaat.
        return "views/book/list";
    }

    /**
     * Toon de pagina voor het boek met het gegeven ean. We halen ook de uitleningen van dit boek op.
     * Om een nieuwe uitlening aan te kunnen maken geven we ook een lijst met members mee, zodat we
     * eventueel een uitlening van een service aan een repository kunnen koppelen.
     *
     * @return De view die we laten zien.
     */
    @RequestMapping(value = "/book/{ean}", method = RequestMethod.GET)
    public String getBookByEAN(@PathVariable Long ean, final ModelMap model) {

        Book book = null;
        try {
            book = bookService.findByEAN(ean);
            logger.debug("getBookByEAN - " + book.toString());
        } catch(BookNotFoundException ex){
            logger.error(ex.getMessage());
        }
        // Bij het lenen van een boek moet je in ons geval een repository selecteren.
        List<Member> members = bookService.findAllMembers();
        logger.debug("getBookByEAN - " + members.size() + " members found");
        // Info over de copies van het boek dat we bekijken.
        List<Copy> copies = bookService.findLendingInfoByBookEAN(ean);
        logger.debug("getBookByEAN - " + copies.size() + " copies found");

        // De gebruiker kan bij het bekijken van het boek via de view kiezen
        // of hij het wil lenen. In de view zit een formulier met een knop om een service te lenen.
        // Daarvoor moet een Loan object ingevuld worden dat we hier meegeven.
        // Dat Loan object wordt via het formulier gePOST naar de LoanController (create).
        Loan loan = new Loan();

        // Zet de gevonden boeken in het model
        // Omdat we hier maar 1 waarde verwachten nemen we listitem 0. Kan tricky zijn.
        model.addAttribute("book", book);
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
        mav.addObject("message", "Het lijkt er op dat we de server op URL <b>" + req.getRequestURI() + "</b> niet konden bereiken. Kan het zijn dat die nog niet gestart is?");
        mav.addObject("exception", ex.getMessage());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error/error");
        return mav;
    }

}
