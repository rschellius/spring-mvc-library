package nl.avans.ivh5.example.springmvc.member;

/**
 * Created by Robin Schellius on 31-8-2016.
 */
public class Member
{
    private int id;
    private String name;
    private String email;

    public Member() {  }

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
        // ID is de auto increment waarde uit de database.
        // wordt hier ingevuld wanneer een Member aangemaakt wordt in de dtb.
        this.id = 0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}