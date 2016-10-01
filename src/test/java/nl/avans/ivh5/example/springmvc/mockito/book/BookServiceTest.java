package nl.avans.ivh5.example.springmvc.mockito.book;

import nl.avans.ivh5.example.springmvc.book.Book;
import nl.avans.ivh5.example.springmvc.book.BookRepository;
import nl.avans.ivh5.example.springmvc.book.BookService;
import nl.avans.ivh5.example.springmvc.loan.LoanRepositoryIF;
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
import static org.mockito.Mockito.*;

/**
 * Created by Robin Schellius on 1-10-2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceTest.class);

    @Mock
    private BookRepository bookRepositoryMock;
    @Mock
    private LoanRepositoryIF loanRepositoryMock;
    @Mock
    private Book bookMock;

    // Deze parameter is niet gemockt.
    private List<Book> bookArrayList;
    private Long ean = 1111L;

    /**
     * Deze method wordt aangeroepen voor het uitvoeren van iedere testcase in deze class.
     */
    @Before
    public void setupMock() {
        logger.info("---- setupMock ----");

        //
        bookMock = mock(Book.class);
        when(bookMock.getEAN()).thenReturn(ean);

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
        when(bookRepositoryMock.findById(anyLong())).thenReturn(bookArrayList);
    }

    @After
    public void tearDown(){
        logger.info("---- tearDown ----");
        // Hier kun je attributen herstellen voor een volgende test.
        // Is in ons geval tot nu toe niet nodig.
    }

    /**
     * Test het vinden van een boek op Ean.
     */
    @Test
    public void testFindByEAN(){
        logger.info("---- testFindByEAN ----");

        // Maak een instantie van de echte BookService, omdat we die willen testen. We gebruiken
        // de gemockte repositories hiervoor. Dat betekent dat wanneer we een call naar de service
        // doen, we controle hebben over wat er als resultaat terug komt, omdat we de repo's gemockt hebben.
        BookService bookServiceToTest = new BookService(bookRepositoryMock, loanRepositoryMock);

        // Roep de methode die we willen testen op de echte service aan.
        // Het resultaat dat terug komt, komt uit onze gemockte repo's, maar gaat wel via de Service.
        // Dat resultaat kunnen we daarna dus valideren. Zo weten we dat de service correct werkte.
        Book result = bookServiceToTest.findByEAN(ean);

        // Voor de service is het nu alsof deze gewoon zijn call tegen de repo's heeft gedaan.
        // Omdat de repositories gemockt zijn weten we wat er terug komt. Dat moeten we valideren.
        assertEquals(result.getEAN(), ean);

//        verify(bookServiceToTest.findByEAN(ean).getEAN()).equals(ean);

    }
}
