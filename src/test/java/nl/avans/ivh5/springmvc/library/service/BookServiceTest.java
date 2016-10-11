package nl.avans.ivh5.springmvc.springmvc.book;

import nl.avans.ivh5.springmvc.common.exception.BookNotFoundException;
import nl.avans.ivh5.springmvc.library.model.Book;
import nl.avans.ivh5.springmvc.library.repository.BookRepository;
import nl.avans.ivh5.springmvc.library.service.BookService;
import nl.avans.ivh5.springmvc.library.service.LoanService;
import nl.avans.ivh5.springmvc.library.service.MemberService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

/**
 * Deze class bevat testcases voor het testen van de BookService class. De BookService is de class
 * die je in de Business Logic laag zou kunnen zien. De service ontvangt aanroepen van de controller
 * (uit de Presentation laag) en kan functionaliteit aanroepen van de BookRepository (in de Data Access laag).
 * Omdat we alleen de BookService willen testen moeten we onafhankelijk zijn van de omringende lagen. We willen
 * dus eigenlijk niet naar de data laag om boeken op te halen, maar we willen dat simuleren - of mocken.
 *
 * Mockito is een framework dat het mogelijk maakt om stubs (of mocks) van bestaande classes te maken, en
 * te bepalen hoe die mocks reageren op aanroepen, bv uit de BookService. Zo hebben we controle over de
 * omliggende classen en kunnen we de functionaliteit van de BookService geïsoleerd testen. Dit is dus
 * feitelijk een unit test.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceTest.class);

    // De volgende parameters worden met Mockito gemockt.
    // We gaan verderop hun gedrag definiëren.
    @Mock
    private BookRepository bookRepositoryMock;
    @Mock
    private LoanService loanServiceMock;
    @Mock
    private MemberService memberServiceMock;
    @Mock
    private Book bookMock;

    // Deze parameters worden niet gemockt. Het zijn meer containers.
    private List<Book> bookArrayList;
    private Long ean = 1111L;

    /**
     * Deze method wordt aangeroepen voor het uitvoeren van iedere testcase in deze class.
     */
    @Before
    public void setupMock() {
        logger.info("---- setupMock ----");

        // Geef aan dat Book gemockt wordt, en bepaal het gedrag.
        // We hoeven niet al het gedrag van Book te bepalen; alleen
        // datgene wat we voor de test nodig hebben.
        bookMock = mock(Book.class);
        when(bookMock.getEAN()).thenReturn(ean);

        // De repository moet een lijst met boeken teruggeven.
        // Hier is het voldoende als daar één boek in zit.
        bookArrayList = new ArrayList<>();
        bookArrayList.add(bookMock);

        // Dit is de mock-versie van onze BookRepository. Hier stellen we in welk resultaat
        // we terug willen krijgen wanneer de BookService de repo gaat gebruiken.
        bookRepositoryMock = mock(BookRepository.class);
        // We gebruiken de BookRespository in de BookService om een boek te vinden.
        // We mocken hier het gedrag van de repository, omdat we de database willen 'stubben'.
        // We geven hier dus aan welke waarde we in de mock retourneren. De methode die daarvoor
        // gestubt moet worden is findById, omdat die in de BookService gebruikt wordt.
        // Wat je retourneert in thenReturn moet voldoen aan het datatype dat findById.
        when(bookRepositoryMock.findByEAN(anyLong())).thenReturn(bookArrayList);
    }

    @After
    public void tearDown(){
        logger.info("---- tearDown ----");
        // Hier kun je attributen herstellen voor een volgende test.
        // Is in ons geval tot nu toe niet nodig.
    }

    /**
     * Test het vinden van een boek op Ean. We testen hier het succespad - we zoeken een boek dat bestaat.
     */
    @Test
    public void testFindByEAN(){
        logger.info("---- testFindByEAN ----");

        // Maak een instantie van de echte BookService, omdat we die willen testen. We gebruiken
        // de gemockte repositories hiervoor. Dat betekent dat wanneer we een call naar de service
        // doen, we controle hebben over wat er als resultaat terug komt, omdat we de repo's gemockt hebben.
        BookService bookServiceToTest = new BookService();
        bookServiceToTest.setBookRepositoryIF(bookRepositoryMock);
        bookServiceToTest.setLoanService(loanServiceMock);
        bookServiceToTest.setMemberService(memberServiceMock);

        // Roep de methode die we willen testen op de echte service aan.
        // Het resultaat dat terug komt, komt uit onze gemockte repo's, maar gaat wel via de Service.
        // Dat resultaat kunnen we daarna dus valideren. Zo weten we dat de service correct werkte.
        Book book = null;
        try {
            book = bookServiceToTest.findByEAN(ean);
            logger.debug("getBookByEAN - " + book.toString());
        } catch(BookNotFoundException ex){
            logger.error(ex.getMessage());
        }

        // Voor de service is het nu alsof deze gewoon zijn call tegen de repo's heeft gedaan.
        // Omdat de repositories gemockt zijn weten we wat er terug komt. Dat moeten we valideren.
        assertEquals(book.getEAN(), ean);

//        verify(bookServiceToTest.findByEAN(ean).getEAN()).equals(ean);

    }

    /**
     * Test het vinden van een boek op Ean.
     * We testen hier het failpad - we zoeken een boek dat NIET bestaat.
     * Dat moet (volgens de specificatie van BookService...!) null teruggeven.
     *
     * Het zou misschien mooier zijn wanneer er een BookNotFound exception kwam.
     */
    @Test
    public void testFindByNonExistingEAN(){
        logger.info("---- testFindByNonExistingEAN ----");

        // Hier willen we dat de repository null gaat retourneren.
        when(bookRepositoryMock.findByEAN(anyLong())).thenReturn(null);

        // Een nieuwe instantie van de BookService
        BookService bookServiceToTest = new BookService();
        bookServiceToTest.setBookRepositoryIF(bookRepositoryMock);
        bookServiceToTest.setLoanService(loanServiceMock);
        bookServiceToTest.setMemberService(memberServiceMock);

        // Zoek een boek dat niet bestaat. (de L geeft een Long aan)
        Book book = null;
        try {
            book = bookServiceToTest.findByEAN(ean);
            logger.debug("getBookByEAN - " + book.toString());
        } catch(BookNotFoundException ex){
            logger.error(ex.getMessage());
        }

        // Het resultaat moet hier null zijn. Met assert kun je checken of dat werkelijk zo is.
        // Als result niet null is volgt er een exception omdat de testcase dan feitelijk failt.
        assertNull(book);
    }


}
