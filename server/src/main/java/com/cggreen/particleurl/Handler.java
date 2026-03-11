package com.cggreen.particleurl;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.cggreen.particleurl.service.UrlService;
import com.cggreen.particleurl.utils.ApiRespone;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    @Override
    public APIGatewayProxyResponseEvent handleRequest(
            APIGatewayProxyRequestEvent request,
            Context context) {
        String method = request.getHttpMethod();
        String path = request.getPath();

        if (method.equals("GET") && path.equals("/")) {
            return ApiRespone.buildResponse(200, "{\"message\":\"Success!\"}");
        } else if (method.equals("POST") && path.equals("/")) {
            return UrlService.createShortUrl(request);
        }
        return ApiRespone.buildResponse(404, "{\"message\":\"Not found.\"}");

    }
}