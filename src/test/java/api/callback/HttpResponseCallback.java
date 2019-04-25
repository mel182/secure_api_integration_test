package api.callback;

/**
 * This http response callback interface
 * @author Melchior Vrolijk
 */
public interface HttpResponseCallback {

    //region On http request response
    /**
     * The http raw response
     * @param responseCode The server response code
     * @param response The server raw response
     */
    void OnResponse(int responseCode, Object response);
    //endregion

    //region On http request failed
    /**
     * The http failed response
     * @param responseCode The server failure response code
     * @param error The server error response
     */
    void OnRequestFailed(int responseCode, String error);
    //endregion
}
