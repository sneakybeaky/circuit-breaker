package com.ninedemons.circuitbreaker.command.http;

import com.ninedemons.circuitbreaker.command.Command;
import com.ninedemons.circuitbreaker.command.exception.CommandException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An operation that uses HTTP
 */
public class SampleHttpCommand implements Command {

    protected Log log = LogFactory.getLog(this.getClass());

    private HttpClient httpClient;
    private HttpMethod httpMethod;

    String response = null;

    public SampleHttpCommand(HttpClient httpClient, HttpMethod httpMethod) {
        this.httpClient = httpClient;
        this.httpMethod = httpMethod;
    }

    public void execute() throws CommandException {
        try {
            int statusCode = httpClient.executeMethod(httpMethod);
            response = httpMethod.getResponseBodyAsString();
            if (statusCode != 200 || response == null) {
                log.error("Received non OK response, status=" + statusCode + " response=" + response);
            } else {
                log.debug("Get successful.");
            }
        } catch (Exception e) {
            throw new CommandException(e);
        } finally {
            httpMethod.releaseConnection();
        }
    }

    public String getResponse() {
        return response;
    }
}
