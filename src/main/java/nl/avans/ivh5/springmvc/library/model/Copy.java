package nl.avans.ivh5.springmvc.library.model;

import java.util.Date;

/**
 * Deze class modelleert een service. Een service is een exemplaar van een boek in de bieb.
 * Een boek kan dus meerdere copies hebben.
 *
 * Je vindt hier meer attributen dan alleen de velden in de tabel Copy in de database, omdat we
 * bij het ophalen van copyinformatie via een SQL JOIN gekoppelde uitleeninformatie ophalen.
 * Die kunnen we dan in één keer bij een boek tonen.
 */
public class Copy {

    private Long copyID;
    private Long ISBN;
    private Long loanID;
    private Date loanDate;
    private Date returnedDate;
    private Long lendingPeriod;
    private Long memberID;
    private String firstName;
    private String lastName;
    private int available;

    public Copy(Long ISBN) {
        // Dit is een verplicht attribuut, omdat dit bij het toevoegen van
        // een service in de database de enige verplichte waarde is. De andere
        // attributen worden automatisch ingevuld (zie library.sql import script)
        // of later via een JOIN met Loan, Book en Member ingevuld.
        this.ISBN = ISBN;

        // Initialiseer de optionele attributen, zodat ze niet NULL zijn.
        // Deze worden bij het gebruik van een Copy ingevuld,
        // bijvoorbeeld in de CopyRepository bij het ophalen van data.
        this.copyID = this.loanID = 0L;
        this.loanDate = this.returnedDate = new Date();
        this.memberID = this.lendingPeriod = 0L;
        this.firstName = this.lastName = "";
        this.available = 0;
    }

    public Long getCopyID() {
        return copyID;
    }

    public void setCopyID(Long copyID) {
        this.copyID = copyID;
    }

    public Long getISBN() {
        return ISBN;
    }

    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }

    public Long getLoanID() {
        return loanID;
    }

    public void setLoanID(Long loanID) {
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

    public Long getLendingPeriod() {
        return lendingPeriod;
    }

    public void setLendingPeriod(Long lendingPeriod) {
        this.lendingPeriod = lendingPeriod;
    }

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
        this.memberID = memberID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Copy{" +
                "copyID=" + copyID +
                ", ISBN=" + ISBN +
                ", loanID=" + loanID +
                ", loanDate=" + loanDate +
                ", returnedDate=" + returnedDate +
                ", lendingPeriod=" + lendingPeriod +
                ", memberID=" + memberID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
