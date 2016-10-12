package nl.avans.ivh5.springmvc.library.controller;

import nl.avans.ivh5.springmvc.common.exception.LoanNotCreatedException;
import nl.avans.ivh5.springmvc.library.model.Loan;
import nl.avans.ivh5.springmvc.library.service.LoanService;
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

    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
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
    public String createLoan(Loan loan, final ModelMap model) throws LoanNotCreatedException {

        logger.debug("createLoan copyID = " + loan.getCopyID() + ", memberID " + loan.getMemberID());
        Loan result = loanService.createLoan(loan);
        // Open de juiste view template als resultaat.
        // Eerst wordt de controller van deze URL aangeroepen;
        // die haalt de updated data op en toont de view.
        return "redirect:/book/" + result.getBookISBN().toString();
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/loan/finish", method = RequestMethod.POST)
    public String finishLoan(Loan loan, final ModelMap model) {

        logger.debug("finishLoan LoanID = " + loan.getLoanID());
        loanService.finishLoan(loan);
        return "redirect:/member/" + loan.getMemberID().toString();
    }

}