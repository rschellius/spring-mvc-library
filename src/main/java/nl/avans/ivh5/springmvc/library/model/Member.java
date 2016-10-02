package nl.avans.ivh5.springmvc.library.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 *
 */
public class Member
{
    // Attributen. Voor info over validatie, zie
    // https://docs.oracle.com/cd/E19798-01/821-1841/gkahq/index.html
    // http://docs.oracle.com/javaee/6/api/javax/validation/constraints/package-summary.html
    // https://spring.io/guides/gs/validating-form-input/

    private int memberID;

    @NotNull
    @Size(min = 1, max = 32)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 32)
    private String lastName;

    @NotNull
    @Size(min = 1, max = 32)
    private String street;

    @NotNull
    @DecimalMin("1")
    private String houseNumber;

    @NotNull
    @Size(min = 1, max = 32)
    private String city;

    @NotNull
    @Size(min = 1, max = 32)
    private String phoneNumber;

    @NotNull
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            +"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message="{invalid.email}")
    private String emailAddress;

    @DecimalMin("0.0")
    private double fine;

    private Date lastUpdated;

    public Member() {  }

    public Member(String firstName, String lastName, String street, String houseNumber, String city, String phoneNumber, String emailAddress, double fine) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.fine = fine;
        // ID is de auto increment waarde uit de database.
        // wordt hier ingevuld wanneer een Member aangemaakt wordt in de dtb.
        this.memberID = 0;
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

    public String getFullName() { return this.firstName + " " + this.lastName; }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public int getMemberID() {
        return memberID;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }
}