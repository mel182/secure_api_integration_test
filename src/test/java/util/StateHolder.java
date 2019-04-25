package util;

import HttpUtil.constant.HttpConstant;
import model.AuthenticatedUser;

/**
 * This is the test state holder of test scenarios
 * @author Melchior Vrolijk
 */
public class StateHolder
{
    public static String authorization_token = "";
    public static String root_user_name = HttpConstant.ROOT_USER_NAME;
    public static String root_user_password = HttpConstant.ROOT_USER_PASSWORD;
    public static AuthenticatedUser created_admin = null;
    public static AuthenticatedUser created_user = null;
    public static int adminDeletedResponseCode = -1;
    public static int userDeletedResponseCode = -1;
    public static final String TEST_ADMIN_USERNAME = "admin@test.com";
    public static final String TEST_ADMIN_PASSWORD = "admin123";
    public static final String TEST_USER_USERNAME = "testUser@test.com";
    public static final String TEST_USER_PASSWORD = "test123";

}
