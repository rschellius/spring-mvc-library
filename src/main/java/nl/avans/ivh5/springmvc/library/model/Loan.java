package nl.avans.ivh5.springmvc.library.model;

import java.util.Date;

/**
 * Created by Robin Schellius on 31-8-2016.
 */
public class Loan
{
    private int loanID;
    private Date loanDate;
    private Date returnedDate;
    private Long bookISBN;
    private String bookTitle;
    private String bookAuthor;
    private String bookEdition;
    private Long copyID;
    private int lendingPeriod;
    private Long memberID;
    private String memberFirstName;
    private String memberLastName;

    public Loan() {  }

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

    public Long getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(Long bookISBN) {
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

    public String getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public Long getCopyID() {
        return copyID;
    }

    public void setCopyID(Long copyID) {
        this.copyID = copyID;
    }

    public int getLendingPeriod() {
        return lendingPeriod;
    }

    public void setLendingPeriod(int lendingPeriod) {
        this.lendingPeriod = lendingPeriod;
    }

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
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