package api;

import HttpUtil.HttpMethod;
import gherkin.deps.com.google.gson.JsonObject;
import sslTrusManager.HttpsTrustManager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Api
{
    public static void call(ApiCall apiCall)
    {
        switch (apiCall.getRequestMethod())
        {
            case HttpMethod.GET:
                executeGETRequest(apiCall);
                break;
            case HttpMethod.POST:
                executePOSTRequest(apiCall);
                break;
            case HttpMethod.PUT:
                executePOSTRequest(apiCall);
                break;
            case HttpMethod.DELETE:
                executeGETRequest(apiCall);
                break;
                default: break;
        }
    }

    private static void executePOSTRequest(ApiCall apiCall)
    {
        try{

            URL requestURL = new URL(apiCall.getRequestURL());
            HttpsTrustManager.allowAllSSL();
            HttpURLConnection httpURLConnection = (HttpURLConnection) requestURL.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod(apiCall.getRequestMethod());
            setHeaderProperties(httpURLConnection,apiCall.getHeaderValues());
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
            String requestBody = createRequestBody(apiCall.getBodyValues());
            outputStreamWriter.write(requestBody);
            outputStreamWriter.flush();
            outputStreamWriter.close();
            outputStream.close();
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

    private static void executeGETRequest(ApiCall apiCall)
    {
        try {
            HttpsTrustManager.allowAllSSL();
            URL url = new URL(apiCall.getRequestURL());
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            setHeaderProperties(connection,apiCall.getHeaderValues());
            connection.setRequestMethod(apiCall.getRequestMethod());
            connection.connect();

            int response_code = connection.getResponseCode();
            String response;

            if (response_code >= 400 && response_code <=500) //since when the httpURLConnection closes/clear the connection immediately if it sees a response code in the 400 range
            {
                response = "";
            }else{
                BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getErrorStream() != null ? connection.getErrorStream() : connection.getInputStream());
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
                    apiCall.getHttpResponseCallback().OnResponse(connection.getResponseCode(),response);
            }else{
                if (apiCall.getHttpResponseCallback() != null)
                    apiCall.getHttpResponseCallback().OnRequestFailed(response_code,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void setHeaderProperties(final HttpURLConnection httpURLConnection, Map<String,String> headerValues)
    {
        headerValues.forEach(httpURLConnection::setRequestProperty);
    }

    private static String createRequestBody(HashMap<String,String> bodyValues)
    {
        JsonObject requestBodyObject = new JsonObject();
        bodyValues.forEach(requestBodyObject::addProperty);
        return requestBodyObject.toString();
    }
}
