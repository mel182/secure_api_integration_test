package api;

import api.callback.HttpResponseCallback;
import server.TargetServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ApiCall
{
    private String requestURL = "";
    private String requestMethod = "";
    private ArrayList<String> parameter;
    private HashMap<String,String> headerValues = new HashMap<>();
    private HashMap<String,String> bodyValues = new HashMap<>();
    private String endPoint = "";
    private HttpResponseCallback httpResponseCallback;

    public ApiCall setEndPoint(String endPoint,String...parameter)
    {
        this.endPoint = endPoint;
        this.parameter = new ArrayList<>(Arrays.asList(parameter));
        setMethod();
        setRequestURL();
        setParamaters();
        return this;
    }

    public ApiCall setHeader(HashMap<String,String> headerValues)
    {
        this.headerValues = headerValues;
        return this;
    }


    public ApiCall setBody(HashMap<String,String> bodyValues)
    {
        this.bodyValues = bodyValues;
        return this;
    }

    public ApiCall setCallback(HttpResponseCallback callback)
    {
        this.httpResponseCallback = callback;
        return this;
    }

    private void setMethod()
    {
        this.requestMethod = getExtractedData()[0];
    }

    private void setRequestURL()
    {
        String endpoint_url = getExtractedData()[1];
        this.requestURL = String.format("%s%s", TargetServer.BASE_URL,endpoint_url);
    }

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

    private String[] getExtractedData()
    {
        final String SPLIT_SIGN = ":";
        final int SPLIT_LIMIT = 2;
        return this.endPoint.split(SPLIT_SIGN,SPLIT_LIMIT);
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public HashMap<String, String> getHeaderValues() {
        return headerValues;
    }

    public HashMap<String, String> getBodyValues() {
        return bodyValues;
    }

    public HttpResponseCallback getHttpResponseCallback() {
        return httpResponseCallback;
    }
}
