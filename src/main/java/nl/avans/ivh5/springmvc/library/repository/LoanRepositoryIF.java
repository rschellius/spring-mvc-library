package nl.avans.ivh5.springmvc.library.repository;

import nl.avans.ivh5.springmvc.library.model.Loan;

import java.util.List;

/**
 * Created by Robin Schellius on 1-10-2016.
 */
public interface LoanRepositoryIF {

    public List<Loan> findAll();
    public List<Loan> findAllByMemberId(int id);
    public List<Loan> findAllByBookEAN(Long ean);
    public Loan findLoanById(int id);
    public Loan create(final Loan loan) throws Exception;
    public void finish(final Loan loan);

}
