package nl.avans.ivh5.springmvc.library.controller;

import com.bol.api.openapi_4_0.Product;
import nl.avans.ivh5.springmvc.library.model.Copy;
import nl.avans.ivh5.springmvc.library.service.CopyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@Controller
public class CopyController {

    private final Logger logger = LoggerFactory.getLogger(CopyController.class);;

    private CopyService copyService;

    @Autowired
    public CopyController(CopyService copyService) {
        this.copyService = copyService;
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

        // De service doet het werkt, en heeft koppelingen met de andere controllers, en
        // met de CopyRepository.
        return copyService.createBookAndCopy(product);
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
        return copyService.findLendingInfoByBookEAN(ean);
    }


}
