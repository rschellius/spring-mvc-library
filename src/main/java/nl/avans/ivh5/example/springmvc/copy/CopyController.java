package nl.avans.ivh5.example.springmvc.copy;

import com.bol.api.openapi_4_0.Entry;
import com.bol.api.openapi_4_0.MediaEntry;
import com.bol.api.openapi_4_0.Product;
import nl.avans.ivh5.example.springmvc.book.Book;
import nl.avans.ivh5.example.springmvc.book.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 *
 */
@Controller
public class CopyController {

    private final Logger logger = LoggerFactory.getLogger(CopyController.class);;

    private CopyRepository copyRepository;
    private BookRepository bookRepository;

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

        String imageUrl = "";
        List<MediaEntry> images = product.getImages();
        if(images.size() > 0){
            imageUrl = images.get(0).getUrl();
        }

        // Als het boek nog niet bestond in de Database moeten we het eerst toevoegen.
        Book newBook = new Book.Builder(ean, product.getTitle(), product.getSpecsTag())
                .shortDescription(product.getShortDescription())
                .edition(product.getSummary())
                .imageUrl(imageUrl)
                .build();
        try {
            bookRepository.create(newBook);
        } catch (Exception ex) {
            // Als het boek al bestaat (EAN/id == primary key) dan volgt een exception.
            // We gaan er hier van uit dat áls er een exception komt, dat door de primary key komt.
            // Dat is natuurlijk tricky. We zouden beter de MySql errorcode kunnen checken.
        }
        // Als het boek in de database zit maken we een nieuwe copie aan.
        // Er kunnen meerdere copies van hetzelfde boek zijn. Die copies zijn ieder wel uniek.
        Copy newCopy = this.copyRepository.create(new Copy(newBook.getEAN()));
        return newCopy;
    }
}
