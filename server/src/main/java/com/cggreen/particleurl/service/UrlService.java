package com.cggreen.particleurl.service;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.cggreen.particleurl.utils.ApiRequest;
import com.cggreen.particleurl.utils.ApiRespone;
import com.cggreen.particleurl.utils.Config;
import com.soundicly.jnanoidenhanced.jnanoid.NanoIdUtils;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.regions.Region;

public class UrlService {
    public static APIGatewayProxyResponseEvent createShortUrl(APIGatewayProxyRequestEvent request) {
        try {
            DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                    .region(Region.US_EAST_1)
                    .build();

            String shortUrl = NanoIdUtils.randomNanoId(7);
            String longUrl = ApiRequest.getBody(request).get("longUrl");
            
            Map<String, AttributeValue> item = new HashMap<>();
            item.put("ShortUrl", AttributeValue.builder().s(shortUrl).build());
            item.put("LongUrl", AttributeValue.builder().s(longUrl).build());

            PutItemRequest itemRequest = PutItemRequest.builder()
                    .tableName(Config.getDynamoTableName())
                    .item(item)
                    .conditionExpression("attribute_not_exists(ShortUrl)")
                    .build();

            dynamoDbClient.putItem(itemRequest);

            return ApiRespone.buildResponse(200, "{\"message\":\"Success!\"}");
        } catch (Exception e) {
            return ApiRespone.buildResponse(500, "{\"message\":\"Fail!\"}");

        }
    }
}
