package nl.avans.ivh5.example.springmvc.home;

import nl.avans.ivh5.example.springmvc.book.BookRepository;
import nl.avans.ivh5.example.springmvc.copy.CopyRepository;
import nl.avans.ivh5.example.springmvc.loan.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
class HomeController {

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);;

    private CopyRepository copyRepository;
    private BookRepository bookRepository;
    private LoanRepository loanRepository;

    @Autowired
    public HomeController(CopyRepository copyRepository, BookRepository bookRepository, LoanRepository loanRepository) {
        this.copyRepository = copyRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    /**
     * Geef een overzicht van alle boeken die te leen zijn. Ieder Book heeft één of meer copies.
     * We willen de book cover tonen, met de titel en de auteur, en informatie over leningen.
     *
     * @param model
     * @return
     */
    @RequestMapping("/")
    String index(Model model) {

        logger.debug("index");

        //
//        List<Copy> copies = copyRepository.findAll();



        // Stuur de tijd mee naar de view - niet omdat het moet, ...
        model.addAttribute("now", LocalDateTime.now());
        // Zet een 'flag' om in Bootstrap header nav het actieve menu item te vinden.
        model.addAttribute("classActiveHome","active");
        return "views/home/index";
    }
}
