package nl.avans.ivh5.example.springmvc.loan;

import java.util.List;

/**
 * Created by Robin Schellius on 1-10-2016.
 */
public interface LoanRepositoryIF {

    public List<Loan> findAll();
    public List<Loan> findAllByMemberId(int id);
    public List<Loan> findAllByBookEAN(Long ean);
    public Loan findLoanById(int id);
    public Loan create(final Loan loan);
    public void finish(final Loan loan);

}