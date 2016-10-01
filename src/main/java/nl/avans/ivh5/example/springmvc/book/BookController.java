package nl.avans.ivh5.example.springmvc.book;

import nl.avans.ivh5.example.springmvc.copy.Copy;
import nl.avans.ivh5.example.springmvc.copy.CopyController;
import nl.avans.ivh5.example.springmvc.loan.Loan;
import nl.avans.ivh5.example.springmvc.member.Member;
import nl.avans.ivh5.example.springmvc.member.MemberController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    // De repository waarin we onze boeken zoeken
    private BookService bookService;
    // De controller waarin we memberinfo opzoeken
    private MemberController memberController;
    // De controller waarin we copyinfo opzoeken
    private CopyController copyController;

    /**
     * Met ModelAttribute kun je attributen (waarden) toevoegen aan het model, zodat
     * je ze in de views kunt gebruiken. Hier kan dat buiten een method .
     * Handig om bv. het 'kruimelpad' (breadcrumbs) of de titel van een pagina in te stellen.
     */
    @ModelAttribute("page")
    public String module() {
        return "BookController";
    }

    // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
    @ModelAttribute("classActiveBooks")
    public String highlightNavMenuItem(){ return "active"; }

    @Autowired
    public BookController(BookService bookService,
                          MemberController memberController,
                          CopyController copyController) {
        this.bookService = bookService;
        this.memberController = memberController;
        this.copyController = copyController;
    }

    /**
     * Lees alle boeken die via de REST API beschikbaar zijn.
     * De REST API is een aparte server die je los van de bibliotheek moet starten.
     *
     * @return view die geopend wordt
     * @see <a href="https://bitbucket.org/AEI-informatica/java-spring-boot-restserver">Bitbucket repo</a>
     */
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String listBooksAtRESTServer(Model model) {

        ArrayList<Book> books;

        //
        // De service laag handelt deze aanroep af
        //
        books = bookService.listBooksAtRESTServer();

        logger.debug("listBooksAtRESTServer", books);

        // Zet de opgevraagde members in het model
        model.addAttribute("books", books);
        // Open de juiste view template als resultaat.
        return "views/member/list";
    }

    /**
     * Toon de pagina voor het boek met het gegeven ean. We halen ook de uitleningen van dit boek op.
     * Om een nieuwe uitlening aan te kunnen maken geven we ook een lijst met members mee, zodat we
     * eventueel een uitlening van een copy aan een member kunnen koppelen.
     *
     * @return De view die we laten zien.
     */
    @RequestMapping(value = "/book/{ean}", method = RequestMethod.GET)
    public String getBookByEAN(@PathVariable Long ean, final ModelMap model) {

        Book book = bookService.findByEAN(ean);
        logger.debug("getBookByEAN - " + book.toString());
        // Bij het lenen van een boek moet je in ons geval een member selecteren.
        List<Member> members = memberController.findAllMembers();
        logger.debug("getBookByEAN - " + members.size() + " members found");
        // Info over de copies van het boek dat we bekijken.
        List<Copy> copies = copyController.findLendingInfoByBookEAN(ean);
        logger.debug("getBookByEAN - " + copies.size() + " copies found");

        // De gebruiker kan bij het bekijken van het boek via de view kiezen
        // of hij het wil lenen. In de view zit een formulier met een knop om een copy te lenen.
        // Daarvoor moet een Loan object ingevuld worden dat we hier meegeven.
        // Dat Loan object wordt via het formulier gePOST naar de LoanController (create).
        Loan loan = new Loan();

        // Zet de gevonden boeken in het model
        // Omdat we hier maar 1 waarde verwachten nemen we listitem 0. Kan tricky zijn.
        model.addAttribute("book", book);
        model.addAttribute("copies", copies);
        model.addAttribute("members", members);
        // Het Loan object dat in het formulier voor het lenen van een boek wordt ingevuld.
        model.addAttribute("loan", loan);
        // Open de juiste view template als resultaat.
        return "views/book/read";
    }


}
