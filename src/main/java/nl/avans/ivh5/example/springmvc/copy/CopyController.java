package nl.avans.ivh5.example.springmvc.copy;

import com.bol.api.openapi_4_0.Entry;
import com.bol.api.openapi_4_0.Product;
import nl.avans.ivh5.example.springmvc.book.Book;
import nl.avans.ivh5.example.springmvc.book.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
@Controller
public class CopyController {

    private final Logger logger = LoggerFactory.getLogger(CopyController.class);;

    // De data repositories die we willen gebruiken
    private CopyRepository copyRepository;
    private BookRepository bookRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    public CopyController(CopyRepository copyRepository, BookRepository bookRepository) {
        this.copyRepository = copyRepository;
        this.bookRepository = bookRepository;
    }

    @ModelAttribute("page")
    public String module() {
        return "copies";
    }

    /**
     * Maak een record voor een Book aan als die nog niet bestaat, en voeg een Copy daarvan toe.
     * De input (RequestBody) is JSON met het product uit de view.
     * Resultaat (ResponseBody) is een Json object met data.
     *
     * With @RequestBody, Spring will maps the POST data to Book POJO (by name) automatically.
     *
     */
    @RequestMapping(value="/createcopy", method = RequestMethod.POST)
    @ResponseBody
    public Copy createBookAndCopy(@RequestBody Product product, final BindingResult bindingResult) {

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
        bookRepository.create(book);

        // Als het boek in de database zit maken we een nieuwe copie aan.
        // Er kunnen meerdere copies van hetzelfde boek zijn. Die copies zijn ieder wel uniek.
        Copy copy = this.copyRepository.create(new Copy(book.getEAN()));
        return copy;
    }



}
