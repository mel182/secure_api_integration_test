package api;

import api.callback.HttpResponseCallback;
import server.TargetServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This api call instance
 * @author Melchior Vrolijk
 */
public class ApiCall
{
    //region Local instances
    private String requestURL = "";
    private String requestMethod = "";
    private ArrayList<String> parameter;
    private HashMap<String,String> headerValues = new HashMap<>();
    private HashMap<String,String> bodyValues = new HashMap<>();
    private String endPoint = "";
    private HttpResponseCallback httpResponseCallback;
    //endregion

    //region Set api call endpoint
    /**
     * Set endpoint and parameter (optional)
     * @param endPoint The endpoint
     * @param parameter The request parameter (optional)
     * @return The {@link ApiCall} instance
     */
    public ApiCall setEndPoint(String endPoint,String...parameter)
    {
        this.endPoint = endPoint;
        this.parameter = new ArrayList<>(Arrays.asList(parameter));
        setMethod();
        setRequestURL();
        setParamaters();
        return this;
    }
    //endregion

    //region Set api call request header
    /**
     * Set request header
     * @param headerValues The request header values
     * @return The {@link ApiCall} instance
     */
    public ApiCall setHeader(HashMap<String,String> headerValues)
    {
        this.headerValues = headerValues;
        return this;
    }
    //endregion

    //region Set api call body
    /**
     * Set request body
     * @param bodyValues The request body values
     * @return The {@link ApiCall} instance
     */
    public ApiCall setBody(HashMap<String,String> bodyValues)
    {
        this.bodyValues = bodyValues;
        return this;
    }
    //endregion

    //region Set http response callback
    /**
     * Set http callback
     * @param callback The {@link HttpResponseCallback} interface instances
     * @return The {@link ApiCall} instance
     */
    public ApiCall setCallback(HttpResponseCallback callback)
    {
        this.httpResponseCallback = callback;
        return this;
    }
    //endregion

    //region Set api call request call back
    /**
     * Set request method
     */
    private void setMethod()
    {
        this.requestMethod = getExtractedData()[0];
    }
    //endregion

    //region Set api call request URL
    /**
     * Set request URL
     */
    private void setRequestURL()
    {
        String endpoint_url = getExtractedData()[1];
        this.requestURL = String.format("%s%s", TargetServer.BASE_URL,endpoint_url);
    }
    //endregion

    //region Set api call request parameter
    /**
     * Add request parameter to request URL
     */
    private void setParamaters()
    {
        if (this.parameter.size() > 0)
        {
            StringBuilder requestURLWithParameters = new StringBuilder();
            char[] requestUrlCharArray = this.requestURL.toCharArray();
            final char PARAMETER_SIGN = '*';

            for (char characterFound : requestUrlCharArray)
            {
                if (characterFound == PARAMETER_SIGN && this.parameter.size() > 0)
                {
                    requestURLWithParameters.append(this.parameter.get(0));
                    this.parameter.remove(0);
                } else {
                    requestURLWithParameters.append(characterFound);
                }
            }

            this.requestURL = requestURLWithParameters.toString();
        }
    }
    //endregion

    //region Get extracted data from endpoint
    /**
     * Extract data from the end point provided
     * @return The split array containing the method and endpoint url
     */
    private String[] getExtractedData()
    {
        final String SPLIT_SIGN = ":";
        final int SPLIT_LIMIT = 2;
        return this.endPoint.split(SPLIT_SIGN,SPLIT_LIMIT);
    }
    //endregion

    //region Get request URL
    /**
     * Set request URL
     * @return The request URL
     */
    String getRequestURL() {
        return requestURL;
    }
    //endregion

    //region Get request method
    /**
     * Get request Method
     * @return The request method
     */
    String getRequestMethod() {
        return requestMethod;
    }
    //endregion

    //region Get api request header values
    /**
     * Get header values
     * @return Hashmap containing the header values
     */
    HashMap<String, String> getHeaderValues() {
        return headerValues;
    }
    //endregion

    //region Get api request body values
    /**
     * Get request body values
     * @return Hashmap containing the request body values
     */
    HashMap<String, String> getBodyValues() {
        return bodyValues;
    }
    //endregion

    //region Get http response callback interface instances
    /**
     * Get the {@link HttpResponseCallback} callback instance
     * @return The {@link HttpResponseCallback} interface callback
     */
    HttpResponseCallback getHttpResponseCallback() {
        return httpResponseCallback;
    }
    //endregion
}
