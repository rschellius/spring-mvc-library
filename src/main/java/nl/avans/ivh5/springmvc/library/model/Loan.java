package nl.avans.ivh5.springmvc.library.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

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

    //
    // The JsonProperty and ApiModelProperty annotations set information in the Swagger documentation.
    //
    //
    @JsonProperty(required = false)
    @ApiModelProperty(notes = "The id of the loan", required = false)
    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    @JsonProperty(required = false)
    @ApiModelProperty(notes = "The date of the loan", required = false)
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

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The book ISBM (EAN)", required = true)
    public void setBookISBN(Long bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    @JsonProperty(required = false)
    @ApiModelProperty(notes = "The book title", required = false)
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    @JsonProperty(required = false)
    @ApiModelProperty(notes = "The book author", required = false)
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