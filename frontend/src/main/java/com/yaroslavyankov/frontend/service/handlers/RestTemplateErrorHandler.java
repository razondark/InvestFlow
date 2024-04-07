package com.yaroslavyankov.frontend.service.handlers;

import com.yaroslavyankov.frontend.service.exception.AccessDeniedException;
import com.yaroslavyankov.frontend.service.exception.GettingProtfolioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new AccessDeniedException("Access token expired");
        }
        if (response.getStatusCode() == HttpStatus.CONFLICT) {
            throw new GettingProtfolioException("Exception during portfolio request");
        }
    }
}
