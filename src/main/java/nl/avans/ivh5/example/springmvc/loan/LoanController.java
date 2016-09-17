package nl.avans.ivh5.example.springmvc.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 *
 */
@Controller
public class LoanController {

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
     * @param ean
     * @return
     */
    public List<Loan> getLoansByBookEAN(Long ean) {
        return loanRepository.findAllByBookEAN(ean);
    }
}
