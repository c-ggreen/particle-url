package com.cggreen.particleurl.utils;


import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.cggreen.particleurl.model.UrlRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiRequest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getLongUrlFromBody(APIGatewayProxyRequestEvent request) {
        String body = request.getBody();
        try {
            return objectMapper.readValue(body, UrlRequest.class).getLongUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getShortUrlFromPath(APIGatewayProxyRequestEvent request) {
        try {
            return request.getPathParameters().get("proxy");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
