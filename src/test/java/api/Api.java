package api;

import gherkin.deps.com.google.gson.JsonObject;
import sslTrusManager.HttpsTrustManager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * This api class that make the api calls
 * @author Melchior Vrolijk
 */
public class Api
{
    //region Call api
    /**
     * The Api call function based on the api call provided
     * @param apiCall The api call containing the request information
     */
    public static void call(ApiCall apiCall)
    {
        makeApiCallRequest(apiCall);
    }
    //endregion

    //region Make api call
    /**
     * Make api call
     * @param apiCall The api call containing the request data
     */
    private static void makeApiCallRequest(ApiCall apiCall)
    {
        try{
            URL requestURL = new URL(apiCall.getRequestURL());
            HttpsTrustManager.allowAllSSL();
            HttpURLConnection httpURLConnection = (HttpURLConnection) requestURL.openConnection();
            httpURLConnection.setRequestMethod(apiCall.getRequestMethod());
            setHeaderProperties(httpURLConnection,apiCall.getHeaderValues());

            if (!apiCall.getBodyValues().isEmpty())
            {
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                String requestBody = createRequestBody(apiCall.getBodyValues());
                outputStreamWriter.write(requestBody);
                outputStreamWriter.flush();
                outputStreamWriter.close();
                outputStream.close();
            }

            httpURLConnection.connect();

            int response_code = httpURLConnection.getResponseCode();
            String response;

            if (response_code >= 400 && response_code <=500) //since when the httpURLConnection closes/clear the connection immediately if it sees a response code in the 400 range
            {
                response = "";
            }else{
                BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getErrorStream() != null ? httpURLConnection.getErrorStream() : httpURLConnection.getInputStream());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int result = bufferedInputStream.read();
                while (result != -1)
                {
                    byteArrayOutputStream.write((byte) result);
                    result = bufferedInputStream.read();
                }
                response = byteArrayOutputStream.toString();
            }

            if (response_code >= 200 && response_code < 300)
            {
                if (apiCall.getHttpResponseCallback() != null)
                    apiCall.getHttpResponseCallback().OnResponse(httpURLConnection.getResponseCode(),response);
            }else{
                if (apiCall.getHttpResponseCallback() != null)
                    apiCall.getHttpResponseCallback().OnRequestFailed(response_code,response);
            }
        }catch (IOException ioException)
        {
            if (apiCall.getHttpResponseCallback() != null)
                apiCall.getHttpResponseCallback().OnRequestFailed(-1,"Error: "+ioException.getMessage());

        }
    }
    //endregion

    //region Set api request header
    /**
     * Set request header properties
     * @param httpURLConnection The target {@link HttpURLConnection} instance
     * @param headerValues The request header values
     */
    private static void setHeaderProperties(final HttpURLConnection httpURLConnection, Map<String,String> headerValues)
    {
        headerValues.forEach(httpURLConnection::setRequestProperty);
    }
    //endregion

    //region Create api request body
    /**
     * Create api call request body
     * @param bodyValues The api request body
     * @return The api call request body
     */
    private static String createRequestBody(HashMap<String,String> bodyValues)
    {
        JsonObject requestBodyObject = new JsonObject();
        bodyValues.forEach(requestBodyObject::addProperty);
        return requestBodyObject.toString();
    }
    //endregion
}
