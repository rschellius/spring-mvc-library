package nl.avans.ivh5.example.springmvc.loan;

import java.util.Date;

/**
 * Created by Robin Schellius on 31-8-2016.
 */
public class Loan
{
    private int loanID;
    private Date loanDate;
    private Date returnedDate;
    private int bookISBN;
    private String bookTitle;
    private String bookAuthor;
    private int bookEdition;
    private int copyID;
    private int lendingPeriod;
    private int memberID;
    private String memberFirstName;
    private String memberLastName;

    public Loan() {  }

    /**
     * Een Loan object wordt gemaakt wanneer het gelezen wordt uit de database, of wanneer er niewe Loan info wordt toegevoegd.
     */
    public Loan(Date loanDate, Date returnedDate, int bookISBN, String bookTitle,
                String bookAuthor, int bookEdition, int copyID, int lendingPeriod, int memberID,
                String memberFirstName, String memberLastName) {
        this.loanID = loanID;
        this.loanDate = loanDate;
        this.returnedDate = returnedDate;
        this.bookISBN = bookISBN;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookEdition = bookEdition;
        this.copyID = copyID;
        this.lendingPeriod = lendingPeriod;
        this.memberID = memberID;
        this.memberFirstName = memberFirstName;
        this.memberLastName = memberLastName;
        this.loanID = 0;    // Wordt ingevuld in LoanRepository bij opvragen van data uit de database (auto-increment ID)
    }

    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    public int getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(int bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(int bookEdition) {
        this.bookEdition = bookEdition;
    }

    public int getCopyID() {
        return copyID;
    }

    public void setCopyID(int copyID) {
        this.copyID = copyID;
    }

    public int getLendingPeriod() {
        return lendingPeriod;
    }

    public void setLendingPeriod(int lendingPeriod) {
        this.lendingPeriod = lendingPeriod;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getMemberFirstName() {
        return memberFirstName;
    }

    public void setMemberFirstName(String memberFirstName) {
        this.memberFirstName = memberFirstName;
    }

    public String getMemberLastName() {
        return memberLastName;
    }

    public void setMemberLastName(String memberLastName) {
        this.memberLastName = memberLastName;
    }
}