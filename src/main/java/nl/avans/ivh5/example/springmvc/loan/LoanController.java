package nl.avans.ivh5.example.springmvc.loan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 */
@Controller
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    private LoanRepository loanRepository;

    @Autowired
    public LoanController(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @ModelAttribute("page")
    public String module() {
        return "loans";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/loan/create", method = RequestMethod.POST)
    public String createLoan(Loan loan, final ModelMap model) {

        logger.debug("createLoan copyID = " + loan.getCopyID() + ", memberID " + loan.getMemberID());
        loanRepository.create(loan);
        // Open de juiste view template als resultaat.
        // Eerst wordt de controlle van deze URL aangeroepen;
        // die haalt de updated data op en toont de view.
        return "redirect:/book/" + loan.getBookISBN().toString();
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/loan/finish", method = RequestMethod.POST)
    public String finishLoan(Loan loan, final ModelMap model) {

        logger.debug("finishLoan LoanID = " + loan.getLoanID());
        loanRepository.finish(loan);
        // Open de juiste view template als resultaat.
        // Eerst wordt de controlle van deze URL aangeroepen;
        // die haalt de updated data op en toont de view.
        return "redirect:/member/" + loan.getMemberID().toString();
    }

}
