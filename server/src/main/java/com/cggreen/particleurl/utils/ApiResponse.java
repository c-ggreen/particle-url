package com.cggreen.particleurl.utils;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class ApiResponse {
    private final static int REDIRECT_STATUS_CODE = 302;

    public static APIGatewayProxyResponseEvent buildResponse(int statusCode, String body) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(Map.of("Content-Type", "application/json"))
                .withBody(body);
    }

    public static APIGatewayProxyResponseEvent buildRedirect(String longUrl) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(REDIRECT_STATUS_CODE)
                .withHeaders(Map.of("Location", longUrl));
    }
}
