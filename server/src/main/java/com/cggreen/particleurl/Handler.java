package com.cggreen.particleurl;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.cggreen.particleurl.service.UrlService;
import com.cggreen.particleurl.utils.ApiResponse;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    @Override
    public APIGatewayProxyResponseEvent handleRequest(
            APIGatewayProxyRequestEvent request,
            Context context) {
        String method = request.getHttpMethod();
        String path = request.getPath();


        if (method.equals("GET") && path.startsWith("/")) {
            return UrlService.getLongUrl(request);
        } else if (method.equals("POST") && path.equals("/")) {
            return UrlService.createShortUrl(request);
        }
        return ApiResponse.buildResponse(404, "{\"message\":\"Not found.\"}");

    }
}