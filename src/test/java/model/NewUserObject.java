package model;

/**
 * This is the new user model
 * @author Melchior Vrolijk
 */
public class NewUserObject
{
    //region Local instances
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String occupation;
    private String password;
    private String sessionToken;
    //endregion

    //region Constructor
    /**
     * Class constructor
     * @param id The user ID
     * @param email The user e-mail address
     * @param firstName The user first name
     * @param lastName The user last name
     * @param occupation The user occupation
     * @param sessionToken The user session token
     */
    public NewUserObject(int id, String email, String firstName, String lastName, String occupation, String sessionToken) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.occupation = occupation;
        this.sessionToken = sessionToken;
    }
    //endregion

    //region Constructor 2
    /**
     * Constuctor 2
     * @param email The user e-mail address
     * @param firstName The user first name
     * @param lastName The user last name
     * @param occupation The user occupation
     * @param password The user password
     */
    public NewUserObject(String email, String firstName, String lastName, String occupation, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.occupation = occupation;
        this.password = password;
    }
    //endregion

    //region Local instances getter
    /**
     * Get user ID
     * @return The user ID
     */
    public int getId() {
        return id;
    }

    /**
     * Get user session token
     * @return The user session token
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * Get user e-mail address
     * @return The user e-mail address
     */
    public String getEmail() {
        return email;
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
     * Get user password
     * @return The user password
     */
    public String getPassword() {
        return password;
    }
    //endregion
}
