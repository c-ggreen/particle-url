package com.cggreen.particleurl;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    @Override
    public APIGatewayProxyResponseEvent handleRequest(
            APIGatewayProxyRequestEvent request,
            Context context) {
        String method = request.getHttpMethod();
        String path = request.getPath();

        if (method.equals("GET") && path.equals("/")) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withHeaders(Map.of("Content-Type", "application/json"))
                    .withBody("{\"message\":\"Success!\"}");
        }

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(404)
                .withHeaders(Map.of("Content-Type", "application/json"))
                .withBody("{\"message\":\"Not found.\"}");
    }
}