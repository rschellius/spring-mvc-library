package nl.avans.ivh5.springmvc.library.service;

import nl.avans.ivh5.springmvc.common.exception.BookNotFoundException;
import nl.avans.ivh5.springmvc.library.model.Book;
import nl.avans.ivh5.springmvc.library.model.Copy;
import nl.avans.ivh5.springmvc.library.model.Loan;
import nl.avans.ivh5.springmvc.library.model.Member;
import nl.avans.ivh5.springmvc.library.repository.BookRepositoryIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Dit is de service class voor boeken. Deze class bevindt zich logisch gezien in de business logic layer.
 * Controllers die informatie over boeken nodig hebben, doen dat door interactie met de BookService.
 * Er zou dus nooit direct een aanroep mogen zijn vanuit een controller naar een DAO (Repository in ons geval).
 */
@Service
public class BookService implements BookServiceIF {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private LoanService loanService;
    private CopyService copyService;
    private MemberService memberService;
    private BookRepositoryIF bookRepositoryIF;

    // Lees een property uit resources/application.properties
    @Value("${avans.library.bookserver.url}")
    private String urlListAllBooks;

    @Autowired
    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }

    @Autowired
    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
    }

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Autowired
    public void setBookRepositoryIF(BookRepositoryIF bookRepositoryIF) {
        this.bookRepositoryIF = bookRepositoryIF;
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

    @Override
    public List<Book> findAll() {
        logger.info("findAll");

        // We krijgen een lijst met boeken terug, maar we verwachten slechts 1 exemplaar.
        List<Book> books = bookRepositoryIF.findAll();
        if(books == null){
            logger.error("findAll - books = null!");
        }
        return books;
    }

    /**
     * Find a book by EAN.
     *
     * @param ean ID of the book
     * @return The book we found
     */
    public Book findByEAN(Long ean) throws BookNotFoundException {
        logger.info("findByEAN ean = " + ean);

        Book book = null;
        // We krijgen een lijst met boeken terug, maar we verwachten slechts 1 exemplaar.
        List<Book> books = bookRepositoryIF.findByEAN(ean);
        if(books == null || 0 == books.size()) {
            throw new BookNotFoundException("Exception!");
        } else {
            book = books.get(0);
            logger.info("findByEAN ean found " + book.getEAN());
            return book;
        }
    }

    /**
     * Maak een boek.
     *
     * @return Het gemaakte boek.
     */
    public Book create(final Book book)  {
        return bookRepositoryIF.create(book);
    }

    /**
     * Maak een uitlening aan op het geselecteerde boek.
     *
     * @param loan
     * @return
     */
    public Loan lendBook(Loan loan){
        Loan result = null;
        try {
            result = loanService.createLoan(loan);
        } catch (Exception ex) {
            logger.error("lendBook - Exception: " + ex.getMessage());
        }
        return result;
    }

    /**
     *
     */
    public List<Member> findAllMembers(){
        return memberService.findAllMembers();
    }

    /**
     *
     */
    public List<Copy> findLendingInfoByBookEAN(Long ean){
        return copyService.findLendingInfoByBookEAN(ean);
    }

}
