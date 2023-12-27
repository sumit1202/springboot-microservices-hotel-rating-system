package com.example.user_microservice.interceptors;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;


@Configuration
@Component
public class HotelFeignClientInterceptor implements RequestInterceptor {

    // OAuth2AuthorizedClientManager is used to authorize OAuth2 requests
    private OAuth2AuthorizedClientManager authorizedClientManager;

    @Override
    public void apply(RequestTemplate req) {

        // Get the access token from the authorized client manager
        String token = authorizedClientManager
                .authorize(OAuth2AuthorizeRequest
                        .withClientRegistrationId("my-internal-client")
                        .principal("internal")
                        .build())
                .getAccessToken()
                .getTokenValue();

        // Add the access token to the request header
        req.header("Authorization", "Bearer " + token);
    }

}
