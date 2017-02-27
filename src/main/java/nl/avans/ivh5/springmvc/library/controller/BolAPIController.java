package nl.avans.ivh5.springmvc.library.controller;

import com.bol.api.openapi_4_0.*;
import com.bol.openapi.OpenApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class BolAPIController {

    private final Logger logger = LoggerFactory.getLogger(BolAPIController.class);

    // Lees een property uit resources/application.properties
    @Value("${bol.com.openapi.v4.apikey}")
    private String apiKey;

    /**
     *
     * @return
     */
    @RequestMapping(value = "/catalogus", method = RequestMethod.GET)
    public String processCatalogusSearch(@RequestParam(value = "search", required = false) String searchTerm, final ModelMap model) {

        logger.debug("processCatalogusSearch zoeken");

        // De waarden die we gaan retourneren
        List<Product> catalogus = new ArrayList<>();

        if(searchTerm != null && !searchTerm.isEmpty()) {
            logger.debug("Zoeken naar " + searchTerm);

            // Gebruik de bol.com OpenApiClient om te zoeken in hun controller.
            // Je moet hiervoor een apikey hebben, die je aanvraagt op
            // https://developers.bol.com. Zie application.properties
            OpenApiClient client = OpenApiClient.withDefaultClient(apiKey);
            SearchResults results = client.searchBuilder()
                    .term(searchTerm)           // De input uit het formulier
                    .term("boek")               // we zoeken hier alleen boeken
                    .limit(50)
                    .includeAttributes()
                    .search();
            catalogus = results.getProducts();  // controller gaat het model in.
        }

        model.addAttribute("search", searchTerm);
        // Zet de gevonden controller waarden in het model
        model.addAttribute("catalogus", catalogus);
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveCatalogus","active");
        model.addAttribute("classActiveBooks","active");
        // Open de juiste view template als resultaat.
        return "views/bolcom/list";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/catalogus/{id}", method = RequestMethod.GET)
    public String searchItemById(@PathVariable String id, final ModelMap model) {

        logger.debug("searchItemById zoeken");

        // Ondanks dat je maar 1 waarde zou verwachten komt er een List terug.
        List<Product> products = new ArrayList<>();

        if(id != null && !id.isEmpty()) {
            logger.debug("Zoeken naar " + id);

            // Gebruik de bol.com OpenApiClient om te zoeken in hun controller.
            // Je moet hiervoor een apikey hebben, die je aanvraagt op
            // https://developers.bol.com. Zie application.properties
            OpenApiClient client = OpenApiClient.withDefaultClient(apiKey);
            SearchResults searchResults = client.searchBuilder()
                    .term(id)
                    .category("8299")       // zoek binnen 'boeken'.
                    .includeAttributes()
                    .limit(1)
                    .search();
            products = searchResults.getProducts();  // controller gaat het model in.

            logger.debug("products.EAN = " + products.get(0).getEAN());
            List<AttributeGroup> attributeGroups = products.get(0).getAttributeGroups();
            Iterator<AttributeGroup> attributeGroupIterator = attributeGroups.iterator();
            while(attributeGroupIterator.hasNext()){
                AttributeGroup group = attributeGroupIterator.next();
                logger.info("Attribute group title = " + group.getTitle());
                Iterator<Entry> entries = group.getAttributes().iterator();
                while (entries.hasNext()){
                    Entry e = entries.next();
                    logger.info(" - entry : key = " + e.getKey() + " value = " + e.getValue() + " label = " + e.getLabel());
                }
            }
        }

        // Zet de gevonden controller waarden in het model
        // Omdat we hier maar 1 waarde verwachten nemen we listitem 0. Kan tricky zijn.
        model.addAttribute("product", products.get(0));
        model.addAttribute("images", products.get(0).getMedia());
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveCatalogus","active");
        model.addAttribute("classActiveBooks","active");
        // Open de juiste view template als resultaat.
        return "views/bolcom/read";
    }

}
