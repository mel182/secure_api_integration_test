package model;

public class NewUserObject
{
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String occupation;
    private String password;
    private String sessionToken;

    public NewUserObject(int id, String email, String firstName, String lastName, String occupation, String sessionToken) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.occupation = occupation;
        this.sessionToken = sessionToken;
    }

    public NewUserObject(String email, String firstName, String lastName, String occupation, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.occupation = occupation;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getPassword() {
        return password;
    }
}
