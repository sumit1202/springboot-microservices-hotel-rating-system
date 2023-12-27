package com.example.user_microservice.interceptors;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

public class RatingRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private OAuth2AuthorizedClientManager authorizedClientManager;

    public RatingRestTemplateInterceptor(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        // Get the access token from the authorized client manager
        String token = authorizedClientManager
                .authorize(OAuth2AuthorizeRequest
                        .withClientRegistrationId("my-internal-client")
                        .principal("internal")
                        .build())
                .getAccessToken()
                .getTokenValue();

        request.getHeaders().add("Authorization", "Bearer " + token);

        return execution.execute(request, body);
    }

}
