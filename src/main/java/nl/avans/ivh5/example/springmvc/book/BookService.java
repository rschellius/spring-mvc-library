package nl.avans.ivh5.example.springmvc.book;

import nl.avans.ivh5.example.springmvc.loan.Loan;
import nl.avans.ivh5.example.springmvc.loan.LoanRepositoryIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Dit is de service class voor boeken. Deze class bevindt zich logisch gezien in de business logic layer.
 * Controllers die informatie over boeken nodig hebben, doen dat door interactie met de BookService.
 * Er zou dus nooit direct een aanroep mogen zijn vanuit een controller naar een DAO (Repository in ons geval).
 */
@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private LoanRepositoryIF loanRepositoryIF;
    private BookRepositoryIF bookRepositoryIF;

    // Lees een property uit resources/application.properties
    @Value("${avans.library.bookserver.url}")
    private String urlListAllBooks;

    @Autowired
    public BookService(BookRepositoryIF bookRepositoryIF, LoanRepositoryIF loanRepositoryIF){
        this.bookRepositoryIF = bookRepositoryIF;
        this.loanRepositoryIF = loanRepositoryIF;
    }

    /**
     * List the books at a given REST webservice.
     *
     * @return The list of books returned from the webservice
     */
    public ArrayList<Book> listBooksAtRESTServer(){
        logger.info("listBooksAtRestServer");

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

        return books;
    }

    /**
     * Find a book by EAN.
     *
     * @param ean ID of the book
     * @return The book we found
     */
    public Book findByEAN(Long ean) {
        logger.info("findByEAN ean = " + ean);

        Book book = null;
        // We krijgen een lijst met boeken terug, maar we verwachten slechts 1 exemplaar.
        List<Book> books = bookRepositoryIF.findById(ean);
        if(books == null){
            logger.error("findByEAN books = null!");
        } else if(books != null && books.size() > 0){
            book = books.get(0);
            logger.info("findByEAN ean found " + book.getEAN());
        }
        return book;
    }

    public boolean lendBook(Loan loan){
//        loanRepositoryIF.create(loan);
        return false;
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
