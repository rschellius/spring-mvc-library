package nl.avans.ivh5.example.springmvc.catalogus;

import com.bol.api.openapi_4_0.Product;
import com.bol.api.openapi_4_0.SearchResults;
import com.bol.openapi.OpenApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CatalogusController {

    private final Logger logger = LoggerFactory.getLogger(CatalogusController.class);;

    // Lees een property uit resources/application.properties
    @Value("${bol.com.openapi.v4.apikey}")
    private String apiKey;

//    @ModelAttribute("searchTerm")
//    public String getSearchString() {
//        return "search";
//    }
//
//    @ModelAttribute("catalogus")
//    public String module() {
//        return "catalogus";
//    }

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

            // Gebruik de bol.com OpenApiClient om te zoeken in hun catalogus.
            // Je moet hiervoor een apikey hebben, die je aanvraagt op
            // https://developers.bol.com. Zie application.properties
            OpenApiClient client = OpenApiClient.withDefaultClient(apiKey);
            SearchResults results = client.searchBuilder()
                    .term(searchTerm)           // De input uit het formulier
                    .term("boek")               // we zoeken hier alleen boeken.
                    .search();
            catalogus = results.getProducts();  // catalogus gaat het model in.
        }

        model.addAttribute("search", searchTerm);
        // Zet de gevonden catalogus waarden in het model
        model.addAttribute("catalogus", catalogus);
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveCatalogus","active");
        model.addAttribute("classActiveBooks","active");
        // Open de juiste view template als resultaat.
        return "views/catalogus/list";
    }

}
