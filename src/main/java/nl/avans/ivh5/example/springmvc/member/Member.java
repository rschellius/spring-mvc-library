package nl.avans.ivh5.example.springmvc.member;

import java.util.Date;

/**
 * Created by Robin Schellius on 31-8-2016.
 */
public class Member
{
    private int memberID;
    private String firstName;
    private String lastName;
    private String street;
    private String houseNumber;
    private String city;
    private String phoneNumber;
    private String emailAddress;
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