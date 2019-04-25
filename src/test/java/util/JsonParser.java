package util;

import model.AuthenticatedUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the JSON parser used within the entire application
 * @author Melchior Vrolijk
 */
public class JsonParser
{
    //region Convert raw server response to authenticated user
    /**
     * Convert raw server response to an authenticated user
     * @param raw_response The raw server response
     * @return The authenticated user object
     */
    public static AuthenticatedUser toAuthenticatedUser(String raw_response)
    {
        try{
            JSONObject jsonObject = new JSONObject(raw_response);
            int id = jsonObject.has(JSONKeys.ID) ? jsonObject.getInt(JSONKeys.ID) : -1;
            String firstName = jsonObject.has(JSONKeys.FIRST_NAME) ? jsonObject.getString(JSONKeys.FIRST_NAME) : "";
            String lastName = jsonObject.has(JSONKeys.LAST_NAME) ? jsonObject.getString(JSONKeys.LAST_NAME) : "";
            String occupation = jsonObject.has(JSONKeys.OCCUPATION) ? jsonObject.getString(JSONKeys.OCCUPATION) : "";
            String email = jsonObject.has(JSONKeys.EMAIL) ? jsonObject.getString(JSONKeys.EMAIL) : "";
            String sessionToken = jsonObject.has(JSONKeys.SESSION_TOKEN) && !jsonObject.isNull(JSONKeys.SESSION_TOKEN) ? jsonObject.getString(JSONKeys.SESSION_TOKEN) : "";

            return new AuthenticatedUser(id,firstName,lastName,occupation,email,sessionToken);
        }catch (JSONException e)
        {
            return null;
        }
    }
    //endregion

    //region Convert raw response to list of authenticated users
    /**
     * Convert raw response to list authenticated user
     * @param raw_response The server raw response
     * @return The list of authenticated users
     */
    public static List<AuthenticatedUser> toUserList(String raw_response)
    {
        List<AuthenticatedUser> authenticatedUserList = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(raw_response);

        for (Object jsonArrayItem : jsonArray)
        {
            JSONObject jsonObject = new JSONObject(jsonArrayItem.toString());

            int id = jsonObject.has(JSONKeys.ID) && !jsonObject.isNull(JSONKeys.ID) ? jsonObject.getInt(JSONKeys.ID) : -1;
            String firsName = jsonObject.has(JSONKeys.FIRST_NAME) && !jsonObject.isNull(JSONKeys.FIRST_NAME) ? jsonObject.getString(JSONKeys.FIRST_NAME) : "";
            String lastName = jsonObject.has(JSONKeys.LAST_NAME) && !jsonObject.isNull(JSONKeys.LAST_NAME) ? jsonObject.getString(JSONKeys.LAST_NAME) : "";
            String occupation = jsonObject.has(JSONKeys.OCCUPATION) && !jsonObject.isNull(JSONKeys.OCCUPATION) ? jsonObject.getString(JSONKeys.OCCUPATION) : "";
            String email = jsonObject.has(JSONKeys.EMAIL) && !jsonObject.isNull(JSONKeys.EMAIL) ? jsonObject.getString(JSONKeys.EMAIL) : "";
            String session = jsonObject.has(JSONKeys.SESSION_TOKEN) && !jsonObject.isNull(JSONKeys.SESSION_TOKEN) ? jsonObject.getString(JSONKeys.SESSION_TOKEN) : "";
            authenticatedUserList.add(new AuthenticatedUser(id,firsName,lastName,occupation,email,session));
        }

        return authenticatedUserList;
    }
    //endregion
}
