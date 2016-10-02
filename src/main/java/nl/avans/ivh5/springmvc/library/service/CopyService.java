package nl.avans.ivh5.springmvc.library.service;

import com.bol.api.openapi_4_0.Entry;
import com.bol.api.openapi_4_0.Product;
import nl.avans.ivh5.springmvc.library.model.Book;
import nl.avans.ivh5.springmvc.library.model.Copy;
import nl.avans.ivh5.springmvc.library.repository.CopyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Dit is de service class voor boeken. Deze class bevindt zich logisch gezien in de business logic layer.
 * Controllers die informatie over boeken nodig hebben, doen dat door interactie met de BookService.
 * Er zou dus nooit direct een aanroep mogen zijn vanuit een controller naar een DAO (Repository in ons geval).
 */
@Service
public class CopyService {

    private final Logger logger = LoggerFactory.getLogger(CopyService.class);;

    // De data repositories die we willen gebruiken
    private CopyRepository copyRepository;
    private BookService bookService = null;

    @Autowired
    private ResourceLoader resourceLoader;

    public CopyService(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    public void setCopyRepository(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Maak een record voor een Book aan als die nog niet bestaat, en voeg een Copy daarvan toe.
     * De input (RequestBody) is JSON met het product uit de view.
     * Resultaat (ResponseBody) is een Json object met data.
     *
     * With @RequestBody, Spring will maps the POST data to Book POJO (by name) automatically.
     *
     */
    public Copy createBookAndCopy(Product product) {

        // Het lijkt onmogelijk om de EAN waarde direct uit product.getEAN() te lezen - is altijd NULL.
        // Bij boeken zit de EAN ook in de ISBN13 waarde in de attributen van een product.
        Long ean = 0L;
        List<Entry> attributeGroups = product.getAttributeGroups().get(0).getAttributes();
        Iterator<Entry> iterator = attributeGroups.iterator();
        while(iterator.hasNext()) {
            Entry element = iterator.next();
            if(element.getKey().equals("ISBN13")) {
                ean = Long.valueOf(element.getValue());
            }
        }

        logger.debug("createBookAndCopy - product.EAN = " + ean + " title = " + product.getTitle());

        // Sommige boeken hebben geen cover image. Om toch een afbeelding te kunnen laten zien
        // voegen we er zelf een toe. Deze code is een
        String bookCoverURL = "http://www.modny73.com/wp-content/uploads/2014/12/shutterstock_172777709-1024x1024.jpg";
        try {
            if(product.getMedia().size()> 0){
                bookCoverURL = product.getMedia().get(0).getUrl();
            } else {
                bookCoverURL = resourceLoader.getResource("http://localhost:8080/static/img/notavailable.jpg").getFile().getCanonicalPath();
            }
        } catch (IOException e) {
            logger.error("Kon cover image niet vinden - " + e.getMessage());
        }

        // Als het boek nog niet bestond in de Database moeten we het eerst toevoegen.
        Book book = new Book.Builder(ean, product.getTitle(), product.getSpecsTag())
                .shortDescription(product.getShortDescription())
                .edition(product.getSummary())
                .imageUrl(bookCoverURL)
                .build();

        // Maak een nieuw boek. De bookRepository handelt de exception af als het boek al bestaat.
        bookService.create(book);

        // Als het boek in de database zit maken we een nieuwe copie aan.
        // Er kunnen meerdere copies van hetzelfde boek zijn. Die copies zijn ieder wel uniek.
        Copy copy = this.copyRepository.create(new Copy(book.getEAN()));
        return copy;
    }

    /**
     * Deze methode heeft geen RequestMapping omdat hij niet via een URL wordt aangeroepen,
     * maar vanuit de BookController.
     *
     * @param ean Het Ean van het boek waarvan we de copies zoeken
     * @return Een lijst met de gevonden copies.
     */
    public List<Copy> findLendingInfoByBookEAN(Long ean) {
        logger.debug("findLendingInfoByBookEAN " + ean);
        List<Copy> result = copyRepository.findLendingInfoByBookEAN(ean);
        return result;
    }

}
