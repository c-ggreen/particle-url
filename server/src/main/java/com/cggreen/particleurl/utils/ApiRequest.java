package com.cggreen.particleurl.utils;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class ApiRequest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, String> getBody(APIGatewayProxyRequestEvent request) {
        String body = request.getBody();

        try {
            return objectMapper.readValue(body, new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
