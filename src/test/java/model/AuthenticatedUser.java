package model;

public class AuthenticatedUser
{
    //Local instances with default value
    private int ID;
    private String firstName;
    private String lastName;
    private String occupation;
    private String email;
    private String password;
    private String sessionToken;

    public AuthenticatedUser(int ID, String firstName, String lastName, String occupation, String email, String sessionToken) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.occupation = occupation;
        this.email = email;
        this.sessionToken = sessionToken;
    }

    public int getID() {
        return ID;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
