package model;

/**
 * This is the authenticated user model
 * @author Melchior Vrolijk
 */
public class AuthenticatedUser
{
    //region Local instances
    private int ID;
    private String firstName;
    private String lastName;
    private String occupation;
    private String email;
    private String password;
    private String sessionToken;
    //endregion

    //region Constructor
    /**
     * Constructor
     * @param ID The user ID
     * @param firstName The user first name
     * @param lastName The user last name
     * @param occupation The user occupation
     * @param email The user e-mail address (username)
     * @param sessionToken The user session token (JWT)
     */
    public AuthenticatedUser(int ID, String firstName, String lastName, String occupation, String email, String sessionToken) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.occupation = occupation;
        this.email = email;
        this.sessionToken = sessionToken;
    }
    //endregion

    //region Local instances getter
    /**
     * Get user ID
     * @return The user ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Get user first name
     * @return The user first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get user last name
     * @return The user last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get user occupation
     * @return The user occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Get user e-mail address
     * @return User e-mail address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get user password
     * @return The user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get user session token
     * @return The user session token
     */
    public String getSessionToken() {
        return sessionToken;
    }
    //endregion
}
