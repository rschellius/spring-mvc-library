package nl.avans.ivh5.springmvc.library.service;

import nl.avans.ivh5.springmvc.common.exception.LoanNotCreatedException;
import nl.avans.ivh5.springmvc.library.model.Loan;
import nl.avans.ivh5.springmvc.library.repository.LoanRepositoryIF;
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

    private LoanRepositoryIF loanRepository = null;
    private CopyService copyService = null;

    public LoanService(LoanRepositoryIF loanRepository) {
        this.loanRepository = loanRepository;
    }

    public void setLoanRepository(LoanRepositoryIF loanRepository) {
        this.loanRepository = loanRepository;
    }

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
    }

    /**
     *
     * @return
     */
    public Loan createLoan(Loan loan) throws LoanNotCreatedException {
        logger.info("createLoan copyID = " + loan.getCopyID() + ", memberID " + loan.getMemberID());
        Loan result = null;
        try {
            result = loanRepository.create(loan);
        } catch (Exception ex) {
            logger.error("Exception in createLoan: " + ex.getMessage());
            throw new LoanNotCreatedException("Could not create the selected loan.");
        }
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
        List<Loan> resultList = loanRepository.findAllByMemberId(id);
        return new ArrayList<Loan>(resultList);
    };
}