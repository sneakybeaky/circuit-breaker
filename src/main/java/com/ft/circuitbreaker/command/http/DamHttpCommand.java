package com.ft.circuitbreaker.command.http;

import com.ft.circuitbreaker.command.Command;
import com.ft.circuitbreaker.command.exception.CommandException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * An operation that uses HTTP
 */
public class DamHttpCommand implements Command {

    protected Log log = LogFactory.getLog(this.getClass());

    private HttpClient httpClient;
    private HttpMethod httpMethod;

    String response = null;

    public DamHttpCommand(HttpClient httpClient, HttpMethod httpMethod) {
        this.httpClient = httpClient;
        this.httpMethod = httpMethod;
    }

    public void execute() throws CommandException {
        try {
            int statusCode = httpClient.executeMethod(httpMethod);
            response = httpMethod.getResponseBodyAsString();
            if (statusCode != 200 || response == null) {
                // Some other error code
                log.error("Received non OK response from DAM. status=" + statusCode + " response=" + response);
                log.debug("Response from DAM: " + response);
            } else {
                // Success (body is "" in this case)
                log.debug("DAM get successful.");
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
