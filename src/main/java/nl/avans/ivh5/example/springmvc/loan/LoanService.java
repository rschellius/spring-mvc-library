package nl.avans.ivh5.example.springmvc.loan;

import nl.avans.ivh5.example.springmvc.copy.CopyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class LoanService {

    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    private LoanRepository loanRepository = null;
    private CopyService copyService = null;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public void setLoanRepository(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
    }

    /**
     *
     * @return
     */
    public Loan createLoan(Loan loan) {
        logger.info("createLoan copyID = " + loan.getCopyID() + ", memberID " + loan.getMemberID());
        Loan result = loanRepository.create(loan);
        return result;
    }

    /**
     *
     * @return
     */
    public void finishLoan(Loan loan) {

        logger.info("finishLoan LoanID = " + loan.getLoanID());
        loanRepository.finish(loan);
    }

    /**
     *
     * @param id
     * @return
     */
    public ArrayList<Loan> findLoansByMemberId(int id){

        logger.info("findAllByMemberId id = " + id);
        List<Loan> resltList = loanRepository.findAllByMemberId(id);
        return new ArrayList<Loan>(resltList);
    };
}