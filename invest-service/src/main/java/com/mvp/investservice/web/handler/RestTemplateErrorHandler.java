package com.mvp.investservice.web.handler;

import com.mvp.investservice.domain.exception.ResourceNotFoundException;
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
        if (response.getStatusCode() == HttpStatus.CONFLICT) {
            String errorMessage = response
                    .getHeaders()
                    .getFirst("error-message");
            throw new ResourceNotFoundException(errorMessage != null ? errorMessage : "Resource not found");
        }
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            String errorMessage = response.getHeaders().getFirst("error-message");
            throw new ResourceNotFoundException(errorMessage != null ? errorMessage : "Resource not found");
        }
    }
}
